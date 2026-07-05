package com.library.service;

import com.library.repository.BookRepository;

import java.util.List;

public class BookService {

    private BookRepository bookRepository;

    public BookService() {
        System.out.println("BookService: instance created.");
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: BookRepository injected via setter.");
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
