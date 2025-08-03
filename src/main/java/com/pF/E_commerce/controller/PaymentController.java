package com.pF.E_commerce.controller;

import com.pF.E_commerce.dto.PaymentRequestDTO;
import com.pF.E_commerce.modal.Order;
import com.pF.E_commerce.modal.User;
import com.pF.E_commerce.repository.OrderRepository;
import com.pF.E_commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final UserService userService;
    private final OrderRepository orderRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByEmail(email);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> pay(@RequestBody PaymentRequestDTO paymentRequest) {
        User user = getCurrentUser();

        Order order = orderRepository
                .findFirstByUserAndStatusOrderByOrderedAtDesc(user, "PENDING")
                .orElseThrow(() -> new RuntimeException("No pending order found"));

        if (paymentRequest.getCardNumber() == null ||
                paymentRequest.getCvv() == null ||
                paymentRequest.getExpiry() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid payment details"));
        }

        order.setStatus("PAID");
        orderRepository.save(order);

        return ResponseEntity.ok(Map.of("message", "Payment successful."));
    }
}