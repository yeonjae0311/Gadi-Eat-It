package com.basic.GADI.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final List<String> excludedUrls;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (excludedUrls != null && excludedUrls.contains(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        Long userId = null;
        String role = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userId = jwtUtil.extractUserId(token);
            role = jwtUtil.extractRole(token);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("토큰이 필요합니다.");
            return;
        }

        try {
            // JWT 검증 및 파싱
            jwtUtil.validateToken(token);
            // 사용자 정보를 request에 추가 (optional)
            request.setAttribute("userId", userId);
            request.setAttribute("role", role);
        } catch (Exception e) {
            response.getWriter().write("Invalid or expired token");
        }

        filterChain.doFilter(request, response);
    }
}