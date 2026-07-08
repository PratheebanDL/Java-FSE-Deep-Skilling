package com.example.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing a simple "Hello World" endpoint.
 *
 * Sample Request : GET http://localhost:8083/hello
 * Sample Response: Hello World!!
 *
 * Note: the exercise referenced the package
 * "com.cognizant.spring-learn.controller" — a hyphen is not a legal
 * character in a Java package name, so this controller lives in
 * "com.example.springlearn.controller" to keep it consistent with the rest
 * of this project. Feel free to rename the package (and folder structure)
 * to match your organization's actual convention, e.g. com.cognizant.springlearn.controller.
 */
@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String sayHello() {
        logger.info("START - sayHello()");

        String response = "Hello World!!";

        logger.info("END - sayHello()");
        return response;
    }
}
