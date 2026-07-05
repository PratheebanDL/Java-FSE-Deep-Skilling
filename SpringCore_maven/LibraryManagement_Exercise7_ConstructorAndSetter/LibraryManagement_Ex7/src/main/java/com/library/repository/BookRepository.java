package com.library.repository;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private final List<String> books = new ArrayList<>();

    public BookRepository() {
        System.out.println("BookRepository: instance created.");
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
