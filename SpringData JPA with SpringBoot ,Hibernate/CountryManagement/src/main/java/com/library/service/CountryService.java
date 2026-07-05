package com.library.service;

import com.library.exception.CountryNotFoundException;
import com.library.model.Country;
import com.library.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer implementing the required Country management features:
 *   - Find a country based on country code
 *   - Add new country
 *   - Update country
 *   - Delete country
 *   - Find list of countries matching a partial country name
 */
@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * Find a country based on country code.
     * Throws CountryNotFoundException if no matching code exists.
     */
    public Country findByCode(String coCode) {
        return countryRepository.findById(coCode)
                .orElseThrow(() -> new CountryNotFoundException(coCode));
    }

    /**
     * Add a new country.
     * Rejects the request if a country with the same code already exists,
     * since co_code is the primary key and save() would otherwise silently
     * overwrite an existing record.
     */
    public Country addCountry(String coCode, String coName) {
        if (countryRepository.existsById(coCode)) {
            throw new DataIntegrityViolationException(
                    "Country already exists with code: " + coCode);
        }
        Country country = new Country(coCode, coName);
        return countryRepository.save(country);
    }

    /**
     * Update an existing country's name.
     * Throws CountryNotFoundException if the code doesn't exist.
     */
    public Country updateCountry(String coCode, String newName) {
        Country existing = findByCode(coCode); // reuses the not-found check
        existing.setCoName(newName);
        return countryRepository.save(existing);
    }

    /**
     * Delete a country by its code.
     * Throws CountryNotFoundException if the code doesn't exist.
     */
    public void deleteCountry(String coCode) {
        Country existing = findByCode(coCode); // reuses the not-found check
        countryRepository.delete(existing);
    }

    /**
     * Find countries whose name contains the given partial text
     * (case-insensitive).
     */
    public List<Country> findByPartialName(String partialName) {
        return countryRepository.findByCoNameContainingIgnoreCase(partialName);
    }
}
