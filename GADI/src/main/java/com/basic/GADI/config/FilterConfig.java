package com.basic.GADI.config;

import com.basic.GADI.config.jwt.JwtAuthenticationFilter;
import com.basic.GADI.config.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter(jwtUtil));
        registrationBean.addUrlPatterns("/api/*");  // 필터를 적용할 URL 패턴 지정
        registrationBean.addInitParameter("excludedUrls", "/api/auth/login,/api/auth/register,/api/main/list");
        return registrationBean;
    }
}