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
 *     {@code
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

    /**
     * Creates a bean for {@code LocationManager}.
     *
     * @return a new instance of {@code LocationManager}
     */
    @Bean
    public LocationManager googleMapsAPIManager() {
        return new LocationManager();
    }

    /**
     * Creates a bean for {@code APIManager}.
     * <p>
     * This method attempts to create a new instance of {@code APIManager}.
     * If an exception occurs during instantiation, it prints the error message
     * and exits the application.
     * </p>
     *
     * @return a new instance of {@code APIManager}, or {@code null} if an error occurs
     */
    @Bean
    public APIManager apiManager() {
        try {
            return new APIManager();
        } catch (Exception e) {
            // Print error
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }
    
}
