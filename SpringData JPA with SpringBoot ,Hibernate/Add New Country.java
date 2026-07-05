====================================================================
NOTE: Java requires each public class to live in its own .java file
matching the class name. This combined file is for reference/copy-paste
only — split it back into 3 separate files before compiling:
  1. CountryNotFoundException.java
  2. CountryService.java
  3. OrmLearnApplication.java
====================================================================


// ====================================================================
// FILE 1: CountryNotFoundException.java
// Path: src/main/java/com/cognizant/springlearn/service/exception/CountryNotFoundException.java
// ====================================================================

package com.cognizant.springlearn.service.exception;

/**
 * Exception thrown when a Country cannot be found for a given country code.
 */
public class CountryNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public CountryNotFoundException(String message) {
        super(message);
    }

    public CountryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}


// ====================================================================
// FILE 2: CountryService.java
// Path: src/main/java/com/cognizant/springlearn/service/CountryService.java
// ====================================================================

package com.cognizant.springlearn.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.springlearn.entity.Country;
import com.cognizant.springlearn.repository.CountryRepository;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryRepository countryRepository;

    /**
     * Fetch all countries.
     */
    @Transactional
    public List<Country> findAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = (List<Country>) countryRepository.findAll();
        LOGGER.info("End");
        return countries;
    }

    /**
     * Fetch a country by its country code.
     *
     * @param countryCode the primary key / country code
     * @return the Country matching the given code
     * @throws CountryNotFoundException if no country is found for the given code
     */
    @Transactional
    public Country findCountryByCode(String countryCode) throws CountryNotFoundException {
        LOGGER.info("Start");

        Optional<Country> result = countryRepository.findById(countryCode);

        if (!result.isPresent()) {
            LOGGER.error("Country not found for code: {}", countryCode);
            throw new CountryNotFoundException("Country not found for code: " + countryCode);
        }

        Country country = result.get();

        LOGGER.debug("Country fetched: {}", country);
        LOGGER.info("End");
        return country;
    }

    /**
     * Add a new country.
     *
     * @param country the Country to be persisted
     */
    @Transactional
    public void addCountry(Country country) {
        LOGGER.info("Start");

        countryRepository.save(country);

        LOGGER.info("End");
    }
}


// ====================================================================
// FILE 3: OrmLearnApplication.java
// Path: src/main/java/com/cognizant/springlearn/OrmLearnApplication.java
// ====================================================================

package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cognizant.springlearn.entity.Country;
import com.cognizant.springlearn.service.CountryService;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static CountryService countryService;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        countryService = (CountryService) context.getBean("countryService");

        getAllCountriesTest();
        testAddCountry();
    }

    /**
     * Test method: fetch a country by code and validate the result.
     */
    private static void getAllCountriesTest() {
        LOGGER.info("Start");
        try {
            Country country = countryService.findCountryByCode("IN");
            LOGGER.debug("Country:{}", country);

            if (country != null && "India".equalsIgnoreCase(country.getCountryName())) {
                LOGGER.info("Test Passed: Country name matches - {}", country.getCountryName());
            } else {
                LOGGER.error("Test Failed: Country name does not match. Found - {}",
                        country != null ? country.getCountryName() : "null");
            }
        } catch (CountryNotFoundException e) {
            LOGGER.error("Country not found", e);
        }
        LOGGER.info("End");
    }

    /**
     * Test method: add a new country, then fetch it back by code
     * to verify it was persisted correctly.
     */
    private static void testAddCountry() {
        LOGGER.info("Start");
        try {
            Country newCountry = new Country();
            newCountry.setCountryCode("ZZ");
            newCountry.setCountryName("Test Land");

            countryService.addCountry(newCountry);

            Country fetchedCountry = countryService.findCountryByCode("ZZ");
            LOGGER.debug("Country added and fetched:{}", fetchedCountry);

            if (fetchedCountry != null && "Test Land".equalsIgnoreCase(fetchedCountry.getCountryName())) {
                LOGGER.info("Test Passed: Country added and verified - {}", fetchedCountry.getCountryName());
            } else {
                LOGGER.error("Test Failed: Added country does not match expected values");
            }
        } catch (CountryNotFoundException e) {
            LOGGER.error("Country not found after adding", e);
        }
        LOGGER.info("End");
    }
}
