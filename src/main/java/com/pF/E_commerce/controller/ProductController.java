package com.pF.E_commerce.controller;

import com.pF.E_commerce.exception.ProductException;
import com.pF.E_commerce.modal.*;
import com.pF.E_commerce.response.Result;
import com.pF.E_commerce.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final UserService userService;
    private final ProductService productService;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByEmail(email);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Result<Product>> getProductById(@PathVariable int Id) {
        Result<Product> product = productService.getProductById(Id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/search/multi")
    public ResponseEntity<Result<String>> searchProductsByKeywords(
            @RequestParam List<String> keywords) {
        User user = getCurrentUser();

        Result<String> result = productService.searchAndAddToCart(user, keywords);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/categories")
    public ResponseEntity<Result<List<Category>>> getAllCategories() {
        Result<List<Category>> categories = productService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Result<Page<Product>>> getProducts(@RequestParam(required = false) Integer categoryId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size) {
        Result<Page<Product>> products = (categoryId == null)
                ? productService.getAllProducts(PageRequest.of(page, size))
                : productService.getProductsByCategory(categoryId, PageRequest.of(page, size));

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
