package com.wyatt.tools.urlshortenerexample;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class Config implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/create")
                .allowedOrigins("http://localhost:8081")
                .exposedHeaders("Access-Control-Allow-Origin");

        // Add more mappings...
    }
}