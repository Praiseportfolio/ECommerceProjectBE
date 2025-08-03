package com.pF.E_commerce.controller;

import com.pF.E_commerce.modal.Cart;
import com.pF.E_commerce.modal.Product;
import com.pF.E_commerce.modal.User;
import com.pF.E_commerce.service.CartService;
import com.pF.E_commerce.service.ProductService;
import com.pF.E_commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByEmail(email);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam int productId, @RequestParam int quantity) {
        Product product = productService.getProductById(productId).getData();
        User user = getCurrentUser();

        Cart cart = cartService.addToCart(user, product, quantity);
        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCartItems() {
        User user = getCurrentUser();
        List<Cart> cartItems = cartService.getCartItemsForUser(user);
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable Long itemId, @RequestParam int quantity) {
        Cart updated = cartService.updateCartItemQuantity(itemId, quantity);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        User user = getCurrentUser();
        cartService.clearCart(user);
        return ResponseEntity.noContent().build();
    }
}