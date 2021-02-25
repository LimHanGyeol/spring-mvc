package com.example.springmvc.config;

import com.example.springmvc.config.AnotherInterceptor;
import com.example.springmvc.config.GreetingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Spring Web Mvc
 * WebMvcConfigurer 1부 Formatter
 * Spring Boot 에서는 이런 설정 클래스를 만들지 않아도 된다.
 * Formatter 를 Bean 으로 만들면 자동으로 Formatter 추가를 해준다.
 * Interceptor 의 Order 를 정해주지 않으면 선언한 순서대로 적용이 된다.
 * Order 의 우선순위는 마이너스일 수록 높다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addFormatter(new PersonFormatter());
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GreetingInterceptor()).order(0);
        registry.addInterceptor(new AnotherInterceptor())
                .addPathPatterns("/hello/**")
                .order(-1);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mobile/**")
                .addResourceLocations("classpath:/mobile/")
                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .resourceChain(true); // 캐쉬를 사용할지 말지. 운영중이라면 true, 개발중이라면 false
                // .addResolver() // 어떤 요청에 해당하는 Resource 탐색
                // .addTransformer() // 응답으로 내보낼 리소스를 변경하는 방법
    }

    // 이걸 사용하면 설정한 컨버터를 사용할 수 있지만, 기본으로 설정되어있는 컨버터 들은 사용을 못한다.
    // @Override
    // public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // }

    // 기본 컨버터를 유지 시키면서 새로운 컨버터를 설정할 경우 사용한다.
    // 하지만 이렇게 수동으로 추가할일이 많지는 않을 것이다.
    // Maven 에서 의존성을 추가하여 컨버터를 이용하는 방법이 가장 많이 사용될 것이다.
    // @Override
    // public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    // }
}
