package com.ramoncasares.paginatedtable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PaginatedTableConfiguration implements WebMvcConfigurer {

    private String[] allowedOrigins = {"http://localhost:4200", "http://localhost:4000"};

    private String[] allowedMethods = {"GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "OPTIONS", "PATCH"};

    private String[] allowedHeaders = {
            "Accept",
            "Accept-Encoding",
            "Accept-Language",
            "Cache-Control",
            "Connection",
            "Content-Length",
            "Content-Type",
            "Cookie",
            "Host",
            "Origin",
            "Pragma",
            "Referer",
            "User-Agent",
            "x-requested-with"};

    private String[] exposedHeaders = {
            "Cache-Control",
            "Connection",
            "Content-Type",
            "Date",
            "Expires",
            "Pragma",
            "Server",
            "Set-Cookie",
            "Transfer-Encoding",
            "X-Content-Type-Options",
            "X-XSS-Protection",
            "X-Frame-Options",
            "X-Application-Context"};

    /**
     * CORS <code>maxAge</code> long property
     */
    private long maxAge = 3600L;

    /**
     * Request headers to be allowed, e.g. content-type,accept,origin,x-requested-with,...
     */


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods(allowedMethods)
                        .allowedHeaders(allowedHeaders)
                        .exposedHeaders(exposedHeaders)
                        .allowCredentials(true)
                        .maxAge(maxAge);
            }

        };
    }

}
