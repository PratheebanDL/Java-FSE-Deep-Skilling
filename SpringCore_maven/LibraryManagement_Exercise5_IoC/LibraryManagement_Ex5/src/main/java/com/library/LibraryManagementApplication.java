package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point that loads the Spring IoC container from
 * applicationContext.xml and verifies that BookService and
 * BookRepository beans were created and wired correctly.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {

        // Load the Spring IoC container using the central configuration file
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the BookService bean managed by the container
        BookService bookService = (BookService) context.getBean("bookService");

        // Exercise the service to confirm the container wired everything correctly
        bookService.listBooks();

        System.out.println();
        bookService.registerBook("Head First Design Patterns");

        System.out.println();
        bookService.listBooks();
    }
}
