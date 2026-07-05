package com.library.repository;

import com.library.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for Country.
 * findById/save/deleteById come from JpaRepository for free.
 * findByCoNameContainingIgnoreCase is derived automatically by
 * Spring Data from the method name (no implementation needed) -
 * it generates: WHERE UPPER(co_name) LIKE UPPER('%partial%')
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    List<Country> findByCoNameContainingIgnoreCase(String partialName);
}
