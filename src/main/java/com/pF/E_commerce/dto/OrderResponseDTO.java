package com.pF.E_commerce.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long orderId,
        double totalAmount,
        String status,
        LocalDateTime orderedAt,
        List<OrderItemDTO> items
) {}