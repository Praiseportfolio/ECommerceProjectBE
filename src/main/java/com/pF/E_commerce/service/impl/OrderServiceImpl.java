package com.pF.E_commerce.service.impl;

import com.pF.E_commerce.dto.OrderResponseDTO;
import com.pF.E_commerce.modal.*;
import com.pF.E_commerce.repository.*;
import com.pF.E_commerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.pF.E_commerce.mapper.OrderMapper.toDto;
import static com.pF.E_commerce.mapper.OrderMapper.toDtoList;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderResponseDTO checkout(User user, Address address) {
        List<Cart> cartItems = cartRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double total = cartItems.stream()
                .mapToDouble(c -> c.getProduct().getSellingPrice() * c.getQuantity())
                .sum();

        Order order = Order.builder()
                .user(user)
                .totalAmount(total)
                .status("PENDING")
                .orderedAt(LocalDateTime.now())
                .shippingAddress(address)
                .build();
        orderRepository.save(order);

        List<OrderItem> orderItems = cartItems.stream().map(cart -> OrderItem.builder()
                .order(order)
                .product(cart.getProduct())
                .quantity(cart.getQuantity())
                .priceAtPurchase(cart.getProduct().getSellingPrice())
                .build()).collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
        order.setItems(orderItems);

        cartRepository.deleteAll(cartItems);
        return toDto(order);
    }

    @Override
    public List<OrderResponseDTO> getUserOrders(User user) {
        List<Order>  order = orderRepository.findByUser(user);
        return toDtoList(order);
    }

    @Override
    public OrderResponseDTO getOrderById(Long id, User user) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }
        return toDto(order);
    }
}