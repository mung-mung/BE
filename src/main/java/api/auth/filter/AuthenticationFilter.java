package api.auth.filter;

import api.common.util.http.HttpResponse;
import api.common.util.jwt.JwtGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import api.auth.dto.AuthUserDetails;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator){
        super();
        setUsernameParameter("email");
        setPasswordParameter("pw");
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        //Spring Security에서 username, password를 검증하기 위해 token에 담음
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 검증을 위한 AuthenticationManager로 전달
        System.out.println("login attempt");
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (JWT 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        AuthUserDetails customerUserDetails = (AuthUserDetails) authentication.getPrincipal();
        String username = customerUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        GrantedAuthority auth = authorities.iterator().next();

        String role = auth.getAuthority();
        String token = jwtGenerator.createJwt(username, role, 60 * 60 * 10000L);

        // Add token to the response header
        response.addHeader("Authorization", "Bearer " + token);

        // Create a response entity with a success message and null data
        ResponseEntity<Object> responseEntity = HttpResponse.successOk("Login successful", null);

        // Convert the response entity to JSON and write it to the response body
        response.setStatus(responseEntity.getStatusCode().value());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
        } catch (IOException e) {
            // Log the exception or handle it as necessary
            e.printStackTrace();
            // Optionally, you might want to re-throw the exception or handle it differently
        }
        System.out.println("Login success");
    }


    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        // 로그인 실패 메시지
        String errorMessage = "Authentication failed";

        // HttpResponse 유틸리티 클래스를 사용하여 401 Unauthorized 응답 생성
        ResponseEntity<Object> responseEntity = HttpResponse.unauthorized(errorMessage, null);

        // 응답 설정
        response.setStatus(responseEntity.getStatusCodeValue());
        response.setContentType("application/json");

        // ObjectMapper를 사용하여 JSON으로 변환 후 응답 본문에 쓰기
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
