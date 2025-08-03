package com.pF.E_commerce.service;

import com.pF.E_commerce.modal.Cart;
import com.pF.E_commerce.modal.User;
import com.pF.E_commerce.modal.Product;

import java.util.List;

public interface CartService {
    Cart addToCart(User user, Product product, int quantity);
    void clearCart(User user);
    List<Cart> getCartItemsForUser(User user);
    Cart updateCartItemQuantity(Long cartItemId, int quantity);
    void removeItem(Long cartItemId);
    List<Cart> addAllToCart(User user, List<Product> products);
}