package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the spring-learn application.
 *
 * @SpringBootApplication is a convenience annotation that combines:
 *  - @Configuration        : marks this class as a source of bean definitions
 *  - @EnableAutoConfiguration : tells Spring Boot to auto-configure beans
 *                               based on the jars on the classpath (e.g.
 *                               spring-boot-starter-web triggers embedded
 *                               Tomcat + Spring MVC auto-configuration)
 *  - @ComponentScan        : scans this package and sub-packages for
 *                               @Component, @Service, @Repository,
 *                               @RestController, etc.
 */
@SpringBootApplication
public class SpringLearnApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Inside main");
		SpringApplication.run(SpringLearnApplication.class, args);
		LOGGER.info("SpringLearnApplication started successfully");
	}
}
