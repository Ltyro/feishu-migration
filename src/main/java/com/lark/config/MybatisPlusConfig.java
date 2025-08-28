package com.lark.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
        basePackages = {"com.lark.service.**.repository"}
)
public class MybatisPlusConfig implements MetaObjectHandler {
    public MybatisPlusConfig() {
    }

    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setFormat(false);
        return performanceInterceptor;
    }

    public void insertFill(MetaObject metaObject) {
    }

    public void updateFill(MetaObject metaObject) {
    }
}
