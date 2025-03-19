package com.example.book_store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.book_store.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByCategoriesContainingIgnoreCase(String category);
    Optional<Book> findByIsbn(String isbn);
}