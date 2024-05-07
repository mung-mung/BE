package api.common.config.security;

import api.auth.filter.AuthenticationFilter;
import api.auth.filter.CustomLogoutFilter;
import api.auth.filter.JwtFilter;
import api.auth.refresh.RefreshRepository;
import api.common.util.http.HttpResponse;
import api.common.util.jwt.JwtGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtGenerator jwtGenerator;
    private final RefreshRepository refreshRepository;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtGenerator jwtGenerator, RefreshRepository refreshRepository) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtGenerator = jwtGenerator;
        this.refreshRepository = refreshRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CORS 설정
        http.cors((cors) -> cors.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setMaxAge(3600L);
            configuration.setExposedHeaders(Collections.singletonList("Authorization"));
            return configuration;
        }));

        // CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // 폼 로그인 및 HTTP 기본 인증 비활성화
        http.formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        // 요청별 권한 설정
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/api/auth/sign-up", "/api/auth/test", "/api/auth/sign-in","/api/auth/reissue").permitAll()
                .requestMatchers("/api/auth/sign-out").authenticated()
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated());

        //Jwt 검증 필터 등록
        http.
                addFilterBefore(new JwtFilter(jwtGenerator), AuthenticationFilter.class);

        // AuthenticationFilter 설정 및 로그인 경로 지정
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(authenticationConfiguration), jwtGenerator, refreshRepository);
        authenticationFilter.setFilterProcessesUrl("/api/auth/sign-in"); // 로그인 경로 설정

        // AuthenticationFilter 추가
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // CustomLogoutFilter 추가
        http.addFilterBefore(new CustomLogoutFilter(jwtGenerator, refreshRepository), LogoutFilter.class);

        // 로그아웃 설정
        http.logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/sign-out"))
                .logoutSuccessHandler((request, response, authentication) -> {
                    // HttpResponse 유틸리티 클래스를 사용하여 성공 응답 생성
                    ResponseEntity<Object> responseEntity = HttpResponse.successOk("Sign out Successed", null);

                    // 응답 상태 및 바디 설정
                    response.setStatus(responseEntity.getStatusCode().value());
                    response.setContentType("application/json");
                    response.getWriter().print(new ObjectMapper().writeValueAsString(responseEntity.getBody()));
                    response.getWriter().flush();
                })
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")); // 필요한 경우 쿠키 삭제

        // 세션 정책 설정
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
