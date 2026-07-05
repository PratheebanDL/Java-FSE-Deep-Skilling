package com.library.service;

import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer that contains business logic and delegates
 * data access to BookRepository.
 *
 * @Service marks this class for component scanning, and @Autowired
 * tells Spring to automatically inject a matching BookRepository bean
 * through the setter - no XML wiring required.
 */
@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService() {
        System.out.println("BookService: instance created (via component scanning).");
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: BookRepository auto-injected via @Autowired.");
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
