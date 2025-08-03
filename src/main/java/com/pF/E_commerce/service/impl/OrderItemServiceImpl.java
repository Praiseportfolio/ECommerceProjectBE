package com.pF.E_commerce.service.impl;


import com.pF.E_commerce.exception.OrderException;
import com.pF.E_commerce.modal.OrderItem;
import com.pF.E_commerce.repository.OrderItemRepository;
import com.pF.E_commerce.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {


    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }


    @Override
    public OrderItem getOrderItemById(Long id) throws Exception {

        System.out.println("------- "+id);
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        if(orderItem.isPresent()){
            return orderItem.get();
        }
        throw new OrderException("Order item not found");
    }
}
