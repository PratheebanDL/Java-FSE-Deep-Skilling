package com.cognizant.springlearn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.CountryService;

/**
 * Controller: CountryController
 * URL       : /country
 *
 * NOTE ON PACKAGE NAME:
 * The requirement lists the package as "com.cognizant.spring-learn.controller".
 * A hyphen ( - ) is not a legal character in a Java package/identifier name,
 * so the artifact/project is called "spring-learn" but the Java package
 * uses "springlearn" (no hyphen): com.cognizant.springlearn.controller
 */
@Controller
public class CountryController {

    /*
     * The "india" bean is defined declaratively in spring-servlet.xml
     * (Spring XML configuration) as:
     *
     *   <bean id="india" class="com.cognizant.springlearn.model.Country">
     *       <property name="code" value="IN" />
     *       <property name="name" value="India" />
     *   </bean>
     *
     * Spring's IoC container creates this bean once at context start-up
     * and @Autowired + @Qualifier("india") injects that exact instance
     * into this controller field.
     */
    @Autowired
    @Qualifier("india")
    private Country india;

    /**
     * GET http://localhost:8083/country
     *
     * @ResponseBody tells Spring MVC to write the return value directly
     * into the HTTP response body (via an HttpMessageConverter) instead
     * of treating the returned value as the name of a view to resolve.
     */
    @RequestMapping(value = "/country", method = RequestMethod.GET)
    @ResponseBody
    public Country getCountryIndia() {
        // Simply return the already-populated bean loaded from the XML config.
        return india;
    }

    /*
     * CountryService looks up a country (case-insensitively) from the
     * country list declared in country.xml (see CountryService for details).
     */
    @Autowired
    private CountryService countryService;

    /**
     * GET http://localhost:8083/countries/{code}
     * e.g. GET http://localhost:8083/countries/in  -> {"code":"IN","name":"India"}
     *
     * @PathVariable extracts the {code} segment of the URL and passes it
     * as the "code" method argument. @GetMapping is shorthand for
     * @RequestMapping(method = RequestMethod.GET).
     *
     * NOTE: the sample request in the requirement uses the singular path
     * "/country/in", but the required method annotation is
     * @GetMapping("/countries/{code}") (plural). Both this controller
     * method and the annotation follow the plural "/countries/{code}"
     * exactly as specified; use http://localhost:8083/countries/in to
     * call it.
     */
    @GetMapping("/countries/{code}")
    @ResponseBody
    public Country getCountry(@PathVariable String code) {
        return countryService.getCountry(code);
    }
}
