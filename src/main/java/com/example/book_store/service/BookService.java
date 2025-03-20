package com.example.book_store.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.book_store.model.Book;
import com.example.book_store.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    public Book createBook(Book book) {
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        return bookRepository.save(book);
    }

    public Book updateBook(String id, Book bookDetails) {
        Book book = getBookById(id);
        
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublisher(bookDetails.getPublisher());
        book.setIsbn(bookDetails.getIsbn());
        book.setDescription(bookDetails.getDescription());
        book.setPrice(bookDetails.getPrice());
        book.setStockQuantity(bookDetails.getStockQuantity());
        book.setCategories(bookDetails.getCategories());
        book.setPublishedDate(bookDetails.getPublishedDate());
        book.setUpdatedAt(LocalDateTime.now());
        
        return bookRepository.save(book);
    }

    public void deleteBook(String id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> searchBooksByCategory(String category) {
        return bookRepository.findByCategoriesContainingIgnoreCase(category);
    }
}
