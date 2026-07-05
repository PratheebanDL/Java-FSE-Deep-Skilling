package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point that loads the Spring context and exercises BookService,
 * so the console shows [AOP - BEFORE] / [AOP - AFTER] log lines
 * surrounding each service/repository method call.
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
