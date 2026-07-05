package com.library.service;

import com.library.repository.BookRepository;

import java.util.List;

/**
 * Service layer that contains business logic and
 * delegates data access to BookRepository.
 * The BookRepository dependency is provided by the Spring IoC
 * container via setter injection (see applicationContext.xml).
 */
public class BookService {

    private BookRepository bookRepository;

    public BookService() {
        System.out.println("BookService: instance created.");
    }

    // Setter method for BookRepository - required for setter-based DI
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: BookRepository injected via setter (Spring IoC container).");
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
