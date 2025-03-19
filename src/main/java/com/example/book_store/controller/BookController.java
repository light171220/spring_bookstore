package com.example.book_store.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.book_store.model.Book;
import com.example.book_store.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.book_store.model.Book> getBookById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @Valid @RequestBody Book bookDetails) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDetails));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<Book>> searchBooksByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(title));
    }

    @GetMapping("/search/author/{author}")
    public ResponseEntity<List<Book>> searchBooksByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(bookService.searchBooksByAuthor(author));
    }

    @GetMapping("/search/category/{category}")
    public ResponseEntity<List<Book>> searchBooksByCategory(@PathVariable String category) {
        return ResponseEntity.ok(bookService.searchBooksByCategory(category));
    }
}
