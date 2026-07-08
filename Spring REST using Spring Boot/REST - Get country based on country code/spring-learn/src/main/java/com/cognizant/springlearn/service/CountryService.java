package com.cognizant.springlearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cognizant.springlearn.model.Country;

/**
 * Service: com.cognizant.springlearn.service.CountryService
 *
 * Holds/looks up the master country list, which is defined declaratively
 * in the Spring XML file "country.xml" as a <util:list> bean named
 * "countryList".
 */
@Service
public class CountryService {

    /*
     * Injected from country.xml:
     *
     *   <util:list id="countryList" value-type="...Country">
     *       <bean class="...Country"><property name="code" value="IN"/>...</bean>
     *       ...
     *   </util:list>
     */
    @Autowired
    @Qualifier("countryList")
    private List<Country> countries;

    /**
     * Returns the Country whose code matches the given code, ignoring case.
     *
     * Implementation uses a lambda expression (Stream API) instead of a
     * manual for-loop:
     *
     *   for (Country c : countries) {
     *       if (c.getCode().equalsIgnoreCase(code)) {
     *           return c;
     *       }
     *   }
     *   return null;
     *
     * is equivalent to the stream/lambda version below.
     */
    public Country getCountry(String code) {
        return countries.stream()
                .filter(country -> country.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No country found for code: " + code));
    }
}
