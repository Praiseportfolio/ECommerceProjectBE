package com.pF.E_commerce.service;

import com.pF.E_commerce.exception.OrderException;
import com.pF.E_commerce.modal.OrderItem;
import com.pF.E_commerce.modal.Product;

public interface OrderItemService {

    OrderItem getOrderItemById(Long id) throws Exception;



}
