package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple REST controller demonstrating Spring Web (Spring MVC on embedded
 * Tomcat, auto-configured by spring-boot-starter-web).
 *
 * Try it after running the application:
 *   http://localhost:8080/hello
 *   http://localhost:8080/hello?name=Cognizant
 */
@RestController
public class HelloController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

	@GetMapping("/hello")
	public String hello(@RequestParam(name = "name", defaultValue = "World") String name) {
		LOGGER.debug("hello() called with name={}", name);
		return "Hello, " + name + "!";
	}
}
