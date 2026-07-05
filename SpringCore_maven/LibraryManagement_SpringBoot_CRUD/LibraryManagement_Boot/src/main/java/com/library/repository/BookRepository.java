package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Book.
 * Extending JpaRepository gives us findAll(), findById(), save(),
 * deleteById(), etc. for free - no implementation needed.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
