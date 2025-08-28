package com.lark.config;

import com.lark.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    public WebMvcConfig() {
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.loginInterceptor).addPathPatterns(new String[]{"/**"}).excludePathPatterns(new String[]{"/login"}).excludePathPatterns(new String[]{"/begin.html"}).excludePathPatterns(new String[]{"/old.html"}).excludePathPatterns(new String[]{"/new.html"}).excludePathPatterns(new String[]{"/error/login_error.html"}).excludePathPatterns(new String[]{"/sheet"});
    }
}
