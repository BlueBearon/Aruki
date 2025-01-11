package com.aruki.aruki;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class AppConfig {

    @Bean
    public LocationManager googleMapsAPIManager() {
        return new LocationManager();
    }

    @Bean
    public APIManager apiManager() {

        try
        {
            return new APIManager();
        } 
        catch (Exception e) {
            //print error
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }
    
}
