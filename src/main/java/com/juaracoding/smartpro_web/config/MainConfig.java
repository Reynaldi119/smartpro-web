package com.juaracoding.smartpro_web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@Configuration
public class MainConfig {
    
    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }
}
