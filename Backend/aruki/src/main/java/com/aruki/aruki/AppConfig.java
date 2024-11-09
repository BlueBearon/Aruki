package com.aruki.aruki;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class AppConfig {

    @Bean
    public GoogleMapsAPIManager googleMapsAPIManager() {
        return new GoogleMapsAPIManager();
    }
    
}
