package com.library.service;

import com.library.repository.BookRepository;

import java.util.List;

/**
 * Service layer that contains business logic and
 * delegates data access to BookRepository.
 */
public class BookService {

    private BookRepository bookRepository;

    // No-args constructor (used if wiring the repository via setter injection)
    public BookService() {
        System.out.println("BookService: instance created.");
    }

    // Constructor for dependency injection (constructor-based wiring)
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: instance created with BookRepository injected.");
    }

    // Setter for dependency injection (setter-based wiring)
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
