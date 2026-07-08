package com.cognizant.springlearn.model;

/**
 * Simple POJO representing a Country.
 *
 * This class is intentionally a plain bean (private fields + public
 * getters/setters, no-arg constructor) because:
 *   1. Spring's XML <bean> configuration wires it up using setter injection
 *      (calls setCode() / setName() based on the <property> tags).
 *   2. Jackson (the JSON library) uses the exact same getter methods
 *      (getCode() / getName()) via reflection to serialize the object
 *      into JSON. The JSON key names are derived from the getter names
 *      (getCode -> "code", getName -> "name").
 */
public class Country {

    private String code;
    private String name;

    public Country() {
        // No-arg constructor required by Spring for setter-based injection
    }

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "Country [code=" + code + ", name=" + name + "]";
    }
}
