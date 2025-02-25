package com.basic.GADI.config;

import com.basic.GADI.config.jwt.JwtAuthenticationFilter;
import com.basic.GADI.config.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    private final JwtUtil jwtUtil;
    private static final String DEVELOP_FRONT_ADDRESS = "http://localhost:5173";

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        List<String> excludedUrls = Arrays.asList("/api/auth/login", "/api/auth/register", "/api/main/list", "/api/auth/send/email", "/api/auth/verify/email");
        registrationBean.setFilter(new JwtAuthenticationFilter(jwtUtil, excludedUrls));
        registrationBean.addUrlPatterns("/api/*");  // 필터를 적용할 URL 패턴 지정
        registrationBean.addInitParameter("excludedUrls", "/api/auth/login,/api/auth/register,/api/main/list");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 쿠키 허용
        config.addAllowedOrigin(DEVELOP_FRONT_ADDRESS); // 허용할 도메인
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.addAllowedMethod("*"); // 모든 HTTP 메서드 허용

        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0); // 필터 순서 설정 (0이면 가장 먼저 실행)
        return bean;
    }

    @Bean
    public FilterRegistrationBean<CookieAttributeFilter> cookieAttributeFilter() {
        FilterRegistrationBean<CookieAttributeFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new CookieAttributeFilter());
        return bean;
    }
}