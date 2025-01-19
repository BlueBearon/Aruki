package com.aruki.aruki;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing) in the backend.
 */
@Configuration
public class CORSConfig {

    /**
     * Configures CORS settings to allow requests from localhost:3000 and the live frontend URL.
     *
     * @return a {@link WebMvcConfigurer} with the CORS configuration.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", "https://arukiurban.netlify.app");
            }
        };
    }
}
