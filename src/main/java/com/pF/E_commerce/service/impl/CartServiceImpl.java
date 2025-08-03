package com.pF.E_commerce.service.impl;

import com.pF.E_commerce.modal.Cart;
import com.pF.E_commerce.modal.Product;
import com.pF.E_commerce.modal.User;
import com.pF.E_commerce.repository.CartRepository;
import com.pF.E_commerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public Cart addToCart(User user, Product product, int quantity) {
        return cartRepository.findByUserAndProduct_Id(user, product.getId())
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + quantity);
                    return cartRepository.save(existing);
                })
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setProduct(product);
                    cart.setQuantity(quantity);
                    cart.setAddedAt(LocalDateTime.now());
                    return cartRepository.save(cart);
                });
    }

    @Override
    public List<Cart> addAllToCart(User user, List<Product> products) {
        List<Integer> productIds = products.stream()
                .map(Product::getId)
                .toList();

        List<Cart> existingCarts = cartRepository.findByUserAndProductIdIn(user, productIds);

        Set<Integer> existingProductIds = existingCarts.stream()
                .map(cart -> cart.getProduct().getId())
                .collect(Collectors.toSet());

        List<Cart> toSave = products.stream()
                .filter(p -> !existingProductIds.contains(p.getId()))
                .map(p -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setProduct(p);
                    cart.setQuantity(1);
                    cart.setAddedAt(LocalDateTime.now());
                    return cart;
                })
                .toList();

        return cartRepository.saveAll(toSave);
    }

    @Override
    public List<Cart> getCartItemsForUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public Cart updateCartItemQuantity(Long cartItemId, int quantity) {
        Cart cart = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    @Override
    public void removeItem(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    @Override
    public void clearCart(User user) {
        List<Cart> userCart = cartRepository.findByUser(user);
        cartRepository.deleteAll(userCart);
    }
}