package api.auth.filter;

import api.auth.dto.AuthUserDetails;
import api.common.util.jwt.JwtGenerator;
import api.user.admin.Admin;
import api.user.enums.Gender;
import api.user.enums.Role;
import api.user.owner.Owner;
import api.user.userAccount.UserAccount;
import api.user.walker.Walker;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        //request에서 Authorization 헤더를 찾음
        String accessToken = request.getHeader("access");
        //Access token 확인
        if (accessToken == null) {
            System.out.println("Access token is null");
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("authorization now");

        try {
            jwtGenerator.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            //Access token이 만료되었을 경우 클라이언트에게 알림
            PrintWriter writer = response.getWriter();
            writer.print("Access token expired");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String category = jwtGenerator.getCategory(accessToken);
        //Access token이 아닌 경우
        if (!category.equals("access")) {
            PrintWriter writer = response.getWriter();
            writer.print("Invalid access token");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //토큰에서 username과 role 획득
        String email = jwtGenerator.getEmail(accessToken);
        String role = jwtGenerator.getRole(accessToken);

        UserAccount user = new Owner();

        //userEntity를 생성하여 값 set
        if (role.equals("OWNER")){
            user = new Owner(email, Role.OWNER, "testpassword", "1234", Gender.MALE, LocalDate.of(1990, 1, 1)); //test values
        }
        else if (role.equals("WALKER")) { //ADMIN 유저 생성 시 수정 필요
            user = new Walker(email, Role.WALKER, "testpassword", "1234", Gender.MALE, LocalDate.of(1990, 1, 1)); //test values
        }
        else if (role.equals("ADMIN")) {
            user = new Admin(email, Role.ADMIN, "testpassword", "1234", Gender.MALE, LocalDate.of(1990, 1, 1)); //test values
        }

        //UserDetails에 회원 정보 객체 담기
        AuthUserDetails authUserDetails = new AuthUserDetails(user);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(authUserDetails, null, authUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
