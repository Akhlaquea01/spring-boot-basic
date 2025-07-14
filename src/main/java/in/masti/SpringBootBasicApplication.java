package in.masti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application Class
 * 
 * This is the entry point of the Spring Boot application. The @SpringBootApplication
 * annotation is a convenience annotation that adds all of the following:
 * - @Configuration: Tags the class as a source of bean definitions for the application context
 * - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings,
 *   other beans, and various property settings
 * - @ComponentScan: Tells Spring to look for other components, configurations, and services
 *   in the in.masti package, allowing it to find the controllers
 * 
 * When the application starts, Spring Boot will:
 * 1. Create an ApplicationContext
 * 2. Register all beans defined in the application
 * 3. Start the embedded web server (Tomcat by default)
 * 4. Deploy the application
 */
@SpringBootApplication
public class SpringBootBasicApplication {

    /**
     * Main method - Application entry point
     * 
     * This method bootstraps the Spring Boot application by:
     * 1. Creating the Spring Application context
     * 2. Starting the embedded web server
     * 3. Running the application
     * 
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBasicApplication.class, args);
    }

}
