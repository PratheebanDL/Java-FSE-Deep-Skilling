====================================================================
NOTE: This combined file is for reference/copy-paste only.
Split it back into separate files at the paths shown below before
building the project. Section 1 is XML, not Java.

  1. applicationContext.xml             (src/main/resources/)
  2. BookService.java                   (src/main/java/com/library/service/)
  3. LibraryManagementApplication.java  (src/main/java/com/library/)

(BookRepository.java is unchanged from the earlier exercise —
 it is not repeated here.)
====================================================================


// ====================================================================
// FILE 1: applicationContext.xml
// Path: src/main/resources/applicationContext.xml
//
// This wires BookRepository into BookService using Setter-based
// Dependency Injection: Spring creates both beans (IoC) and calls
// BookService.setBookRepository(...) automatically because of the
// <property name="bookRepository" ref="bookRepository" /> entry.
// ====================================================================

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                            http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Repository bean -->
    <bean id="bookRepository" class="com.library.repository.BookRepository" />

    <!-- Service bean: BookRepository is injected via the setter below -->
    <bean id="bookService" class="com.library.service.BookService">
        <property name="bookRepository" ref="bookRepository" />
    </bean>

</beans>


// ====================================================================
// FILE 2: BookService.java
// Path: src/main/java/com/library/service/BookService.java
//
// The setBookRepository() method below is what Spring calls during
// Dependency Injection, based on the <property> tag in the XML config.
// ====================================================================

package com.library.service;

import java.util.List;

import com.library.repository.BookRepository;

/**
 * Service class that contains business logic for managing books.
 * BookRepository is injected by Spring via the setter method
 * (Setter-based Dependency Injection), as configured in
 * applicationContext.xml.
 */
public class BookService {

    private BookRepository bookRepository;

    /**
     * Setter method used by Spring's IoC container to inject
     * the BookRepository dependency.
     */
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: BookRepository dependency injected successfully.");
    }

    public void addBook(String bookTitle) {
        System.out.println("BookService: Processing add request for - " + bookTitle);
        bookRepository.addBook(bookTitle);
    }

    public List<String> listBooks() {
        System.out.println("BookService: Processing list request");
        return bookRepository.getAllBooks();
    }

    public void removeBook(String bookTitle) {
        System.out.println("BookService: Processing remove request for - " + bookTitle);
        bookRepository.removeBook(bookTitle);
    }
}


// ====================================================================
// FILE 3: LibraryManagementApplication.java
// Path: src/main/java/com/library/LibraryManagementApplication.java
//
// Verifies dependency injection: if BookRepository was NOT injected
// correctly, calling bookService.addBook() would throw a
// NullPointerException instead of working.
// ====================================================================

package com.library;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.service.BookService;

public class LibraryManagementApplication {

    public static void main(String[] args) {
        // Spring's IoC container loads bean definitions and performs
        // Dependency Injection based on applicationContext.xml
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        BookService bookService = (BookService) context.getBean("bookService");

        // If DI worked, these calls succeed without a NullPointerException,
        // since bookRepository inside bookService is no longer null.
        bookService.addBook("Effective Java");
        bookService.addBook("Clean Code");
        bookService.addBook("Spring in Action");

        List<String> books = bookService.listBooks();
        System.out.println("Books in library: " + books);

        bookService.removeBook("Clean Code");

        List<String> updatedBooks = bookService.listBooks();
        System.out.println("Books in library after removal: " + updatedBooks);

        System.out.println("Dependency Injection test passed: BookRepository was successfully injected into BookService.");
    }
}
