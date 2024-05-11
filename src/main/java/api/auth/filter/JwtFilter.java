package api.auth.filter;

import api.auth.dto.AuthUserDetails;
import api.common.util.auth.jwt.JwtGenerator;
import api.common.util.http.HttpResponse;
import api.user.admin.Admin;
import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.userAccount.UserAccount;
import api.user.walker.Walker;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtGenerator jwtGenerator;

    public JwtFilter(JwtGenerator jwtGenerator){
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization header: " + authorizationHeader);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, HttpResponse.unauthorized("No valid authorization header found", null));
            return;
        }

        String accessToken = authorizationHeader.substring(7);
        try {
            jwtGenerator.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, HttpResponse.unauthorized("Access token expired", null));
            return;
        } catch (Exception e) {
            sendErrorResponse(response, HttpResponse.badRequest("Invalid access token", null));
            return;
        }

        String category = jwtGenerator.getCategory(accessToken);
        if (!category.equals("access")) {
            sendErrorResponse(response, HttpResponse.unauthorized("Invalid access token", null));
            return;
        }

        //토큰에서 username과 role 획득
        String email = jwtGenerator.getEmail(accessToken);
        String role = jwtGenerator.getRole(accessToken);

        Authentication authToken = getAuthentication(role, email);
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private static Authentication getAuthentication(String role, String email) {
        UserAccount user = null;

        //userEntity를 생성하여 값 set
        if (role.equals("OWNER")){
            user = new Owner(email, "test userName", Role.OWNER, "testpassword", "1234", Gender.MALE, LocalDate.of(1990, 1, 1)); //test values
        }
        else if (role.equals("WALKER")) { //ADMIN 유저 생성 시 수정 필요
            user = new Walker(email, "test userName",Role.WALKER, "testpassword", "1234", Gender.MALE, LocalDate.of(1990, 1, 1)); //test values
        }
        else if (role.equals("ADMIN")) {
            user = new Admin(email,"test userName", Role.ADMIN, "testpassword", "1234", Gender.MALE, LocalDate.of(1990, 1, 1)); //test values
        }

        //UserDetails에 회원 정보 객체 담기
        AuthUserDetails authUserDetails = new AuthUserDetails(user);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(authUserDetails, null, authUserDetails.getAuthorities());
        return authToken;
    }

    private void sendErrorResponse(HttpServletResponse response, ResponseEntity<Object> responseEntity) throws IOException {
        response.setStatus(responseEntity.getStatusCode().value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        new ObjectMapper().writeValue(writer, responseEntity.getBody());
        writer.flush();
    }
}
