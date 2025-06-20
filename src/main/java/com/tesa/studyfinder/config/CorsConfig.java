package com.tesa.studyfinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")          // allow CORS on all paths
                        .allowedOrigins("*")        // allow all origins
                        .allowedMethods("*")        // allow all HTTP methods (GET, POST, etc)
                        .allowedHeaders("*")        // allow all headers
                        .allowCredentials(false);   // or true if you need cookies/auth
            }
        };
    }
}
