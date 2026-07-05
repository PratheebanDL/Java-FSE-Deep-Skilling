package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point that loads the Spring context (annotation-driven,
 * via component scanning) and verifies BookService and BookRepository
 * were detected and wired automatically.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {

        // Component scanning (declared in applicationContext.xml) finds
        // @Service and @Repository classes and registers them as beans.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // Beans are found by type (or by default id = uncapitalized class name)
        BookService bookService = context.getBean(BookService.class);

        bookService.listBooks();

        System.out.println();
        bookService.registerBook("Head First Design Patterns");

        System.out.println();
        bookService.listBooks();
    }
}
