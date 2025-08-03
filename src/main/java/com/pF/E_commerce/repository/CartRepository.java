package com.pF.E_commerce.repository;

import com.pF.E_commerce.modal.Cart;
import com.pF.E_commerce.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndProduct_Id(User user, int productId);
    List<Cart> findByUser(User user);
    List<Cart> findByUserAndProductIdIn(User user, List<Integer> productIds);
}

