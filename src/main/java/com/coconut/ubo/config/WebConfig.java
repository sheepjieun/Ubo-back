package com.coconut.ubo.config;

import com.coconut.ubo.web.argumentresolver.LoginUserArgumentResolver;
import com.coconut.ubo.web.interceptor.LogInterceptor;
import com.coconut.ubo.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


/**
 * 스프링부트-리액트 CORS 연동
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 프론트엔드-백엔드 CORS 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("https://ourboddari.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true)
                .maxAge(3600); // 1 hour
    }

    // Argument Resolver 추가
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }

    // Interceptor 추가
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()) // 인터셉터 등록
                .order(1) // 인터셉터 호출 순서 지정. 낮을 수록 먼저 호출
                .addPathPatterns("/**") // 인터셉터를 적용할 URL 패턴 지정
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 인터셉터제어 제외할 패턴 지정

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/signup", "/login", "/logout",
                        "/css/**", "/*.ico", "/error" );
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
