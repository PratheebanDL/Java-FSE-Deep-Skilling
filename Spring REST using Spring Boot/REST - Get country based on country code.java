// ==================== FILE: country.xml ====================

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:util="http://www.springframework.org/schema/util"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://www.springframework.org/schema/beans
https://www.springframework.org/schema/beans/spring-beans.xsd
http://www.springframework.org/schema/util
https://www.springframework.org/schema/util/spring-util.xsd">

<bean id="india" class="com.cognizant.springlearn.model.Country">
<property name="code" value="IN" />
<property name="name" value="India" />
</bean>

<bean id="unitedStates" class="com.cognizant.springlearn.model.Country">
<property name="code" value="US" />
<property name="name" value="United States" />
</bean>

<bean id="unitedKingdom" class="com.cognizant.springlearn.model.Country">
<property name="code" value="GB" />
<property name="name" value="United Kingdom" />
</bean>

<util:list id="countryList" value-type="com.cognizant.springlearn.model.Country">
<ref bean="india" />
<ref bean="unitedStates" />
<ref bean="unitedKingdom" />
</util:list>

</beans>


// ==================== FILE: CountryService.java ====================

package com.cognizant.springlearn.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.cognizant.springlearn.model.Country;

@Service
public class CountryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

	@SuppressWarnings("unchecked")
	public Country getCountry(String code) {
		LOGGER.info("Start");

		ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
		List<Country> countryList = (List<Country>) context.getBean("countryList", List.class);

		Optional<Country> result = countryList.stream()
				.filter(country -> country.getCode().equalsIgnoreCase(code))
				.findFirst();

		Country country = result.orElse(null);

		LOGGER.info("End");
		return country;
	}
}


// ==================== FILE: CountryController.java ====================

package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.CountryService;

@RestController
public class CountryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

	@Autowired
	private CountryService countryService;

	@RequestMapping("/country")
	public Country getCountryIndia() {
		LOGGER.info("Start");

		ApplicationContext context = new ClassPathXmlApplicationContext("country-config.xml");
		Country country = context.getBean("india", Country.class);

		LOGGER.info("End");
		return country;
	}

	@GetMapping("/countries/{code}")
	public Country getCountry(@PathVariable String code) {
		LOGGER.info("Start");

		Country country = countryService.getCountry(code);

		LOGGER.info("End");
		return country;
	}
}
