package com.flowfinance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        final String VERCEL_FRONTEND_URL = "https://flow-finance-frontend.vercel.app";

        registry.addMapping("/**") 
                .allowedOrigins(VERCEL_FRONTEND_URL) 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") 
                .allowedHeaders("*") 
                .allowCredentials(true)
                .maxAge(3600); 
    }
}