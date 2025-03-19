package com.example.book_store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.book_store.model.Order;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
    List<Order> findByStatus(Order.OrderStatus status);
}