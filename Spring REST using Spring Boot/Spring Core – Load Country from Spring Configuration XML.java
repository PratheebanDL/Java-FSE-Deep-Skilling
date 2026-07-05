// ==================== FILE: date-format.xml ====================

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://www.springframework.org/schema/beans
https://www.springframework.org/schema/beans/spring-beans.xsd">

<bean id="dateFormat" class="java.text.SimpleDateFormat">
<constructor-arg value="dd/MM/yyyy" />
</bean>

</beans>


// ==================== FILE: SpringLearnApplication.java ====================

package com.cognizant.springlearn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringLearnApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting SpringLearnApplication...");

		SpringApplication.run(SpringLearnApplication.class, args);

		displayDate();

		LOGGER.info("SpringLearnApplication started successfully.");
	}

	private static void displayDate() {
		ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");

		SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);

		try {
			Date date = format.parse("31/12/2018");
			System.out.println(date);
		} catch (ParseException e) {
			LOGGER.error("Error parsing date", e);
		}
	}
}


// ==================== FILE: application.properties ====================

server.port=8081
