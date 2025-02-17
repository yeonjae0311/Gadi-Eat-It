package com.basic.GADI.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Value("${jwt.secretKey}")
    private String signatureKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userEmail = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userEmail = jwtUtil.extractUserEmail(token);
        }

        try {
            // JWT 검증 및 파싱
            jwtUtil.validateToken(token);
            // 사용자 정보를 request에 추가 (optional)
            request.setAttribute("userEmail", userEmail);
        } catch (Exception e) {
            response.getWriter().write("Invalid or expired token");
        }

        filterChain.doFilter(request, response);
    }
}