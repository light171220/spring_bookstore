package com.example.book_store.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.book_store.model.Book;
import com.example.book_store.model.Order;
import com.example.book_store.model.Order.OrderItem;
import com.example.book_store.repository.BookRepository;
import com.example.book_store.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setPaymentStatus(Order.PaymentStatus.PENDING);
        
        // Calculate total and validate stock
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            Book book = bookRepository.findById(item.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + item.getBookId()));
            
            if (book.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock for book: " + book.getTitle());
            }
            
            // Update book details in order item
            item.setBookTitle(book.getTitle());
            item.setPrice(book.getPrice());
            item.setSubtotal(book.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            
            // Update stock
            book.setStockQuantity(book.getStockQuantity() - item.getQuantity());
            bookRepository.save(book);
            
            // Add to total
            total = total.add(item.getSubtotal());
        }
        
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(String id, Order.OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order updatePaymentStatus(String id, Order.PaymentStatus paymentStatus) {
        Order order = getOrderById(id);
        order.setPaymentStatus(paymentStatus);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }
}
