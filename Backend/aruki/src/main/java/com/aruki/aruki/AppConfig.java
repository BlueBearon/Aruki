package com.aruki.aruki;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

/**
 * The {@code AppConfig} class is a configuration class for the Spring application.
 * <p>
 * This class is used to define beans and other configuration settings for the application.
 * </p>
 * <p>
 * Each {@code AppConfig} object contains:
 * <ul>
 *   <li>A bean for {@code LocationManager}</li>
 *   <li>A bean for {@code APIManager}</li>
 * </ul>
 * </p>
 * <p>
 * This class provides methods to create and configure beans, allowing for
 * easy management and dependency injection of the beans.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     @Configuration
 *     public class AppConfig {
 *         @Bean
 *         public LocationManager googleMapsAPIManager() {
 *             return new LocationManager();
 *         }
 *         
 *         @Bean
 *         public APIManager apiManager() {
 *             return new APIManager();
 *         }
 *     }
 * </pre>
 * </p>
 * <p>
 * This class can be used in conjunction with other configuration classes to
 * set up the application context and manage beans.
 * </p>
 * 
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.Bean
 */
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
