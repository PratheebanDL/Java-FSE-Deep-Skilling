====================================================================
NOTE: A real Maven project needs these as SEPARATE files at the
paths shown below. This combined file is for reference/copy-paste
only — split it back out before building the project. It is saved
with a .java extension for convenience, but sections 1 and 2 below
are XML content (pom.xml and applicationContext.xml), not Java.

  1. pom.xml                                  (project root)
  2. applicationContext.xml                   (src/main/resources/)
  3. BookRepository.java                      (src/main/java/com/library/repository/)
  4. BookService.java                         (src/main/java/com/library/service/)
  5. LibraryManagementApplication.java        (src/main/java/com/library/)
====================================================================


// ====================================================================
// FILE 1: pom.xml
// Path: pom.xml  (project root)
// ====================================================================

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                              http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.library</groupId>
    <artifactId>LibraryManagement</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>LibraryManagement</name>
    <description>Library Management System using Spring Core</description>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <spring.version>5.3.30</spring.version>
    </properties>

    <dependencies>
        <!-- Spring Core -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring Context (needed for ApplicationContext, bean wiring) -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring Beans -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Logging -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.36</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>1.7.36</version>
        </dependency>
    </dependencies>

    <build>
        <finalName>LibraryManagement</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>${maven.compiler.source}</source>
                    <target>${maven.compiler.target}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>


// ====================================================================
// FILE 2: applicationContext.xml
// Path: src/main/resources/applicationContext.xml
// ====================================================================

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                            http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Repository bean -->
    <bean id="bookRepository" class="com.library.repository.BookRepository" />

    <!-- Service bean, wired with the repository -->
    <bean id="bookService" class="com.library.service.BookService">
        <property name="bookRepository" ref="bookRepository" />
    </bean>

</beans>


// ====================================================================
// FILE 3: BookRepository.java
// Path: src/main/java/com/library/repository/BookRepository.java
// ====================================================================

package com.library.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for storing and retrieving Book data.
 * For this exercise, an in-memory list simulates the data store.
 */
public class BookRepository {

    private List<String> books = new ArrayList<>();

    public void addBook(String bookTitle) {
        books.add(bookTitle);
        System.out.println("BookRepository: Added book - " + bookTitle);
    }

    public List<String> getAllBooks() {
        System.out.println("BookRepository: Fetching all books");
        return books;
    }

    public boolean removeBook(String bookTitle) {
        boolean removed = books.remove(bookTitle);
        if (removed) {
            System.out.println("BookRepository: Removed book - " + bookTitle);
        } else {
            System.out.println("BookRepository: Book not found - " + bookTitle);
        }
        return removed;
    }
}


// ====================================================================
// FILE 4: BookService.java
// Path: src/main/java/com/library/service/BookService.java
// ====================================================================

package com.library.service;

import java.util.List;

import com.library.repository.BookRepository;

/**
 * Service class that contains business logic for managing books.
 * Delegates data access to BookRepository (wired via Spring).
 */
public class BookService {

    private BookRepository bookRepository;

    // Setter used by Spring for dependency injection (see applicationContext.xml)
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
// FILE 5: LibraryManagementApplication.java
// Path: src/main/java/com/library/LibraryManagementApplication.java
// ====================================================================

package com.library;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.service.BookService;

/**
 * Main class to load the Spring ApplicationContext and verify
 * that BookService and BookRepository beans are wired correctly.
 */ 
public class LibraryManagementApplication {

    public static void main(String[] args) {
        // Load Spring context from applicationContext.xml (src/main/resources)
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the BookService bean
        BookService bookService = (BookService) context.getBean("bookService");

        // Test the wiring: add a couple of books and list them
        bookService.addBook("Effective Java");
        bookService.addBook("Clean Code");
        bookService.addBook("Spring in Action");

        List<String> books = bookService.listBooks();
        System.out.println("Books in library: " + books);

        bookService.removeBook("Clean Code");

        List<String> updatedBooks = bookService.listBooks();
        System.out.println("Books in library after removal: " + updatedBooks);
    }
}
