package com.pF.E_commerce.repository;

import com.pF.E_commerce.modal.Order;
import com.pF.E_commerce.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);// In OrderRepository.java
    Optional<Order> findFirstByUserAndStatusOrderByOrderedAtDesc(User user, String status);
}