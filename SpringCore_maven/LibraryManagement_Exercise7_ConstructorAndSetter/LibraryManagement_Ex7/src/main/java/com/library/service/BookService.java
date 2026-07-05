package com.library.service;

import com.library.repository.BookRepository;

import java.util.List;

/**
 * Service layer that supports BOTH constructor injection and
 * setter injection for its BookRepository dependency, so the
 * Spring configuration can demonstrate either (or both) styles.
 */
public class BookService {

    private BookRepository bookRepository;

    // No-args constructor - used only if neither constructor-arg
    // nor setter injection is configured.
    public BookService() {
        System.out.println("BookService: no-args constructor called.");
    }

    // Constructor injection: Spring calls this when a
    // <constructor-arg> is configured in applicationContext.xml
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: BookRepository injected via CONSTRUCTOR.");
    }

    // Setter injection: Spring calls this when a
    // <property> is configured in applicationContext.xml.
    // If both constructor-arg and property are configured for the
    // same bean, Spring calls the constructor first, then the setter -
    // the setter's value becomes the final one held by the bean.
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: BookRepository injected via SETTER.");
    }

    public void listBooks() {
        System.out.println("BookService: listing all available books:");
        List<String> books = bookRepository.findAll();
        for (String book : books) {
            System.out.println(" - " + book);
        }
    }

    public void registerBook(String title) {
        System.out.println("BookService: registering new book request.");
        bookRepository.addBook(title);
    }
}
