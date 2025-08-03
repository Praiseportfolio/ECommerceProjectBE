package com.pF.E_commerce.service;

import com.pF.E_commerce.dto.OrderResponseDTO;
import com.pF.E_commerce.modal.Address;
import com.pF.E_commerce.modal.User;

import java.util.List;

public interface OrderService {
    OrderResponseDTO checkout(User user, Address address);
    List<OrderResponseDTO> getUserOrders(User user);
    OrderResponseDTO getOrderById(Long id, User user);
}