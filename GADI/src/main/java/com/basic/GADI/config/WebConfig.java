package com.basic.GADI.config;

import com.basic.GADI.common.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins(DEVELOP_FRONT_ADDRESS)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
//                .exposedHeaders("Authorization", "Content-Type", "Location")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:C:/GadiEatIt/upload/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/admin/**")  // 특정 경로만 보호
                .excludePathPatterns("/api/main/**"); // 예외 경로 추가 가능
    }
}