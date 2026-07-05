====================================================================
NOTE: Java requires each public type to live in its own .java file
matching its name. This combined file is for reference/copy-paste
only — split it back into separate files before compiling:
  1. CountryRepository.java
  2. OrmLearnApplication.java (updated with new test methods)
====================================================================


// ====================================================================
// FILE 1: CountryRepository.java
// Path: src/main/java/com/cognizant/springlearn/repository/CountryRepository.java
// ====================================================================

package com.cognizant.springlearn.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cognizant.springlearn.entity.Country;

public interface CountryRepository extends CrudRepository<Country, String> {

    // ----------------------------------------------------------------
    // 1. Search-as-you-type: countries whose name CONTAINS the given
    //    text, e.g. findByCountryNameContaining("ou") matches
    //    "Bouvet Island", "Djibouti", "Guadeloupe", etc.
    // ----------------------------------------------------------------
    List<Country> findByCountryNameContaining(String partialName);

    // ----------------------------------------------------------------
    // 2. Same search, but results returned in ascending order of
    //    country name.
    // ----------------------------------------------------------------
    List<Country> findByCountryNameContainingOrderByCountryNameAsc(String partialName);

    // ----------------------------------------------------------------
    // 3. Alphabet index: countries whose name STARTS WITH the given
    //    letter, e.g. findByCountryNameStartingWith("Z") matches
    //    "Zambia", "Zimbabwe".
    // ----------------------------------------------------------------
    List<Country> findByCountryNameStartingWith(String letter);
}


// ====================================================================
// FILE 2: OrmLearnApplication.java (updated)
// Path: src/main/java/com/cognizant/springlearn/OrmLearnApplication.java
// ====================================================================

package com.cognizant.springlearn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cognizant.springlearn.entity.Country;
import com.cognizant.springlearn.repository.CountryRepository;
import com.cognizant.springlearn.service.CountryService;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static CountryService countryService;
    private static CountryRepository countryRepository;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        countryService = (CountryService) context.getBean("countryService");
        countryRepository = (CountryRepository) context.getBean("countryRepository");

        getAllCountriesTest();
        testAddCountry();
        testSearchCountriesContaining();
        testSearchCountriesContainingOrderedByName();
        testSearchCountriesStartingWith();
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

    /**
     * Test method: search-as-you-type using "ou" — should match
     * Bouvet Island, Djibouti, Guadeloupe, South Georgia and the
     * South Sandwich Islands, Luxembourg, South Sudan,
     * French Southern Territories, United States Minor Outlying
     * Islands, South Africa.
     */
    private static void testSearchCountriesContaining() {
        LOGGER.info("Start");
        List<Country> countries = countryRepository.findByCountryNameContaining("ou");
        for (Country country : countries) {
            LOGGER.debug("Country matching 'ou': {} - {}", country.getCountryCode(), country.getCountryName());
        }
        LOGGER.info("Total matches found: {}", countries.size());
        LOGGER.info("End");
    }

    /**
     * Test method: same "ou" search but results ordered ascending
     * by country name.
     */
    private static void testSearchCountriesContainingOrderedByName() {
        LOGGER.info("Start");
        List<Country> countries = countryRepository.findByCountryNameContainingOrderByCountryNameAsc("ou");
        for (Country country : countries) {
            LOGGER.debug("Country (ordered): {} - {}", country.getCountryCode(), country.getCountryName());
        }
        LOGGER.info("End");
    }

    /**
     * Test method: alphabet index search — countries starting
     * with 'Z' should return Zambia and Zimbabwe.
     */
    private static void testSearchCountriesStartingWith() {
        LOGGER.info("Start");
        List<Country> countries = countryRepository.findByCountryNameStartingWith("Z");
        for (Country country : countries) {
            LOGGER.debug("Country starting with 'Z': {} - {}", country.getCountryCode(), country.getCountryName());
        }
        LOGGER.info("End");
    }
}
