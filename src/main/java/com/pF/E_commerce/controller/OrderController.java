package com.pF.E_commerce.controller;

import com.pF.E_commerce.dto.OrderResponseDTO;
import com.pF.E_commerce.modal.Address;
import com.pF.E_commerce.modal.Order;
import com.pF.E_commerce.modal.User;
import com.pF.E_commerce.service.OrderService;
import com.pF.E_commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByEmail(email);
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDTO> checkout(@RequestBody Address address) {
        User user = getCurrentUser();
        OrderResponseDTO order = orderService.checkout(user, address);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {
        User user = getCurrentUser();
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long orderId) {
        User user = getCurrentUser();
        return ResponseEntity.ok(orderService.getOrderById(orderId, user));
    }
}