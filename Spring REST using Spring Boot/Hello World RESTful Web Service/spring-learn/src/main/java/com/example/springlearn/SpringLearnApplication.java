package com.example.springlearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main Spring Boot entry point for the spring-learn application.
 *
 * Starting this class boots an embedded Tomcat server (see
 * src/main/resources/application.properties for the port) and exposes the
 * REST endpoints declared under com.example.springlearn.controller, e.g.
 * HelloController's GET /hello.
 *
 * It also still demonstrates the earlier exercise: loading a shared
 * SimpleDateFormat bean ("dd/MM/yyyy") from a Spring XML configuration file
 * (date-format.xml) instead of creating a new SimpleDateFormat instance in
 * every place it is needed. This runs once at startup via displayDate().
 */
@SpringBootApplication
public class SpringLearnApplication {

    public static void main(String[] args) {
        // Demonstrate the standalone XML-based bean lookup from the earlier exercise
        new SpringLearnApplication().displayDate();

        // Start the Spring Boot application (embedded web server + REST controllers)
        SpringApplication.run(SpringLearnApplication.class, args);
    }

    /**
     * Loads the Spring XML application context, retrieves the "dateFormat"
     * bean, uses it to parse a date string, and prints the resulting Date.
     */
    public void displayDate() {
        // Load the Spring container using the XML configuration file
        // located on the classpath at src/main/resources/date-format.xml
        ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");

        // Retrieve the singleton SimpleDateFormat bean defined in the XML
        SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);

        try {
            // Parse the string '31/12/2018' using the bean-provided formatter
            Date date = format.parse("31/12/2018");
            System.out.println("Parsed date: " + date);
            System.out.println("Formatted back using the same bean: " + format.format(date));
        } catch (ParseException e) {
            System.err.println("Unable to parse date: " + e.getMessage());
        } finally {
            // Clean up the context if it supports it
            if (context instanceof ClassPathXmlApplicationContext) {
                ((ClassPathXmlApplicationContext) context).close();
            }
        }
    }
}
