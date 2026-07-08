package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point that loads the Spring application context
 * from applicationContext.xml and exercises the BookService bean.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {

        // Load beans defined in src/main/resources/applicationContext.xml
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the BookService bean by its id
        BookService bookService = (BookService) context.getBean("bookService");

        // Test the wired service -> repository chain
        bookService.listBooks();

        System.out.println();
        bookService.registerBook("Head First Design Patterns");

        System.out.println();
        bookService.listBooks();
    }
}
