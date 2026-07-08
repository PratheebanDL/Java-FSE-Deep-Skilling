package com.example.springlearn;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Demonstrates loading a shared SimpleDateFormat bean ("dd/MM/yyyy") from a
 * Spring XML configuration file (date-format.xml) instead of creating a new
 * SimpleDateFormat instance in every place it is needed.
 */
public class SpringLearnApplication {

    public static void main(String[] args) {
        SpringLearnApplication app = new SpringLearnApplication();
        app.displayDate();
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
