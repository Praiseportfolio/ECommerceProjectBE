package com.pF.E_commerce.dto;

public record OrderItemDTO(
        Long productId,
        String title,
        int quantity,
        double priceAtPurchase
) {}