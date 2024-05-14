package api.auth.filter;

import api.auth.refresh.RefreshEntity;
import api.auth.refresh.RefreshRepository;
import api.common.util.http.HttpResponse;
import api.common.util.auth.jwt.JwtGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import api.auth.dto.AuthUserDetails;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final RefreshRepository refreshRepository;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator, RefreshRepository refreshRepository){
        super();
        setUsernameParameter("email");
        setPasswordParameter("pw");
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = obtainUsername(request);
        String password = obtainPassword(request);

        //Spring Security에서 email, password를 검증하기 위해 token에 담음
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        System.out.println("login attempt");
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (JWT 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        //토큰에 담을 정보 가져오기 (email, role)
        AuthUserDetails customerUserDetails = (AuthUserDetails) authentication.getPrincipal();
        String email = customerUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        GrantedAuthority auth = authorities.iterator().next();

        String role = auth.getAuthority();

        // Create access token and request token
        String accessToken = jwtGenerator.createJwt("access", email, role, 60 * 10000L);
        String refreshToken = jwtGenerator.createJwt("refresh", email, role, 86400000L); //24h

        //서버에 Refresh 토큰 저장
        addRefreshEntity(email, refreshToken);

        //Add access token to header and request token to cookie
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(createCookie(refreshToken));
        response.setStatus(HttpStatus.OK.value());

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        ResponseEntity<Object> responseEntity = HttpResponse.successOk("Signin finished successfully", tokenMap);
        response.setStatus(responseEntity.getStatusCode().value());
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), responseEntity.getBody());
        System.out.println("Login success");
    }


    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        ResponseEntity<Object> responseEntity = HttpResponse.unauthorized("SignIn failed", null);
        response.setStatus(responseEntity.getStatusCode().value());
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), responseEntity.getBody());
    }

    //Refresh token을 추가하기 위한 Cookie 생성
    private Cookie createCookie(String value) {
        Cookie cookie = new Cookie("Refresh", value);
        cookie.setMaxAge(24*60*60);
//        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    private void addRefreshEntity(String email, String refreshToken) {
        LocalDateTime expirationDateTime = LocalDateTime.now(ZoneId.systemDefault()).plusDays(1);
        RefreshEntity refreshEntity = new RefreshEntity(email, refreshToken, expirationDateTime);
        refreshRepository.save(refreshEntity);
    }
}
