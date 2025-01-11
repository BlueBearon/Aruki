package com.aruki.aruki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The {@code ArukiApplication} class is the entry point for the Spring Boot application.
 * <p>
 * This class contains the {@code main} method which is used to launch the application.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     public class ArukiApplication {
 *         public static void main(String[] args) {
 *             SpringApplication.run(ArukiApplication.class, args);
 *         }
 *     }
 * </pre>
 * </p>
 * <p>
 * This class can be used in conjunction with other configuration and component classes
 * to set up and run the application.
 * </p>
 * 
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 */
@SpringBootApplication
public class ArukiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArukiApplication.class, args);
	}

}
