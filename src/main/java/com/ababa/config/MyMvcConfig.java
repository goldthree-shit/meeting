package com.ababa.config;

import com.ababa.interceptors.CorsInterceptor;
import com.ababa.interceptors.JWTInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@MapperScan("com.ababa.config")
public class MyMvcConfig implements WebMvcConfigurer {
    @Autowired
    private JWTInterceptor jwtInterceptor;
    @Autowired
    private CorsInterceptor corsInterceptor;
    @Value("${user.file.path}")
    private String filePath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/meetingroom/**","/meeting/**")
                .excludePathPatterns("/user/login","/user/register","/swagger-ui.html","/index.html","/file/**"
                                     ,"/css/**", "/js/**", "/img/**","/fonts/**","/favicon.ico","/register.html","/"
                                     ,"/meeting/getRecodingMeeting","/meeting/addParticipant/**","/meeting/addParticipant");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+filePath);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowCredentials(true)
//                .allowedHeaders("*")
//                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
//                .maxAge(3600);
//    }


}
