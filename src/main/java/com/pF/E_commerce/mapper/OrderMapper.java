package com.pF.E_commerce.mapper;

import com.pF.E_commerce.dto.OrderItemDTO;
import com.pF.E_commerce.dto.OrderResponseDTO;
import com.pF.E_commerce.modal.Order;

import java.util.List;

public class OrderMapper {

    public static OrderResponseDTO toDto(Order order) {
        List<OrderItemDTO> items = order.getItems().stream()
                .map(item -> new OrderItemDTO(
                        (long) item.getProduct().getId(),
                        item.getProduct().getTitle(),
                        item.getQuantity(),
                        item.getPriceAtPurchase()
                ))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderedAt(),
                items
        );
    }

    public static List<OrderResponseDTO> toDtoList(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toDto)
                .toList();
    }
}