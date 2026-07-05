// ==================== FILE: Country.java ====================

package com.cognizant.springlearn.model;

public class Country {

	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}


// ==================== FILE: country-config.xml ====================

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://www.springframework.org/schema/beans
https://www.springframework.org/schema/beans/spring-beans.xsd">

<bean id="india" class="com.cognizant.springlearn.model.Country">
<property name="code" value="IN" />
<property name="name" value="India" />
</bean>

</beans>


// ==================== FILE: CountryController.java ====================

package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.model.Country;

@RestController
public class CountryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

	@RequestMapping("/country")
	public Country getCountryIndia() {
		LOGGER.info("Start");

		ApplicationContext context = new ClassPathXmlApplicationContext("country-config.xml");
		Country country = context.getBean("india", Country.class);

		LOGGER.info("End");
		return country;
	}
}
