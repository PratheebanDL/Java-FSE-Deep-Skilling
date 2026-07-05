package com.library.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulates a data-access layer for books.
 * @Repository tells Spring's component scanner to detect this class
 * and register it as a bean automatically (no XML <bean> entry needed).
 */
@Repository
public class BookRepository {

    private final List<String> books = new ArrayList<>();

    public BookRepository() {
        System.out.println("BookRepository: instance created (via component scanning).");
        // seed with some sample data
        books.add("Effective Java");
        books.add("Spring in Action");
        books.add("Clean Code");
    }

    public void addBook(String title) {
        books.add(title);
        System.out.println("BookRepository: added book -> " + title);
    }

    public List<String> findAll() {
        System.out.println("BookRepository: fetching all books from data store.");
        return books;
    }
}
