package com.library.exception;

/**
 * Thrown when a country lookup, update, or delete is attempted
 * for a country code that doesn't exist.
 */
public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(String coCode) {
        super("Country not found with code: " + coCode);
    }
}
