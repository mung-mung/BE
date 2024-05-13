package api.auth.filter;

import api.auth.refresh.RefreshRepository;
import api.common.util.auth.jwt.JwtGenerator;
import com.fasterxml.jackson.databind.ObjectMapper; // JSON 변환을 위한 Jackson 라이브러리
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CustomLogoutFilter extends GenericFilterBean {
    private final JwtGenerator jwtGenerator;
    private final RefreshRepository refreshRepository;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 변환을 위해 ObjectMapper 인스턴스 생성

    public CustomLogoutFilter(JwtGenerator jwtGenerator, RefreshRepository refreshRepository) {
        this.jwtGenerator = jwtGenerator;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("/api/auth/sign-out") || !request.getMethod().equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        String refresh = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Refresh".equals(cookie.getName())) {
                    refresh = cookie.getValue();
                }
            }
        }

        if (refresh == null) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "No refresh token provided", null);
            return;
        }

        try {
            jwtGenerator.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Refresh token expired", null);
            return;
        }

        String category = jwtGenerator.getCategory(refresh);
        if (!"refresh".equals(category) || !refreshRepository.existsByRefresh(refresh)) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid refresh token", null);
            return;
        }

        refreshRepository.deleteByRefresh(refresh);
        Cookie cookie = new Cookie("Refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        sendResponse(response, HttpServletResponse.SC_OK, "Sign out finished successfully", null);
    }

    private void sendResponse(HttpServletResponse response, int status, String message, Object data) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", status);
        responseBody.put("message", message);
        responseBody.put("data", data);
        out.print(objectMapper.writeValueAsString(responseBody));
        out.flush();
    }
}
