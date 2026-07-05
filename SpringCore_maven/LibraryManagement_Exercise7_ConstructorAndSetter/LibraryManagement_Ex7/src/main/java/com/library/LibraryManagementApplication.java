package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point that loads the Spring context and verifies that
 * BookService received its BookRepository dependency through
 * BOTH constructor injection and setter injection, as configured
 * in applicationContext.xml.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        BookService bookService = (BookService) context.getBean("bookService");

        bookService.listBooks();

        System.out.println();
        bookService.registerBook("Head First Design Patterns");

        System.out.println();
        bookService.listBooks();
    }
}
