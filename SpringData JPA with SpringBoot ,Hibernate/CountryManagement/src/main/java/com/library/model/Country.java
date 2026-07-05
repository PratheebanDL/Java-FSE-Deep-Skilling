package com.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA entity mapped to the "country" table.
 * co_code is the natural primary key (2-letter ISO country code).
 */
@Entity
@Table(name = "country")
public class Country {

    @Id
    @Column(name = "co_code", length = 2, nullable = false)
    private String coCode;

    @Column(name = "co_name", nullable = false)
    private String coName;

    public Country() {
    }

    public Country(String coCode, String coName) {
        this.coCode = coCode;
        this.coName = coName;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }
}
