package com.pF.E_commerce.service;

import com.pF.E_commerce.modal.Category;
import com.pF.E_commerce.modal.Product;
import com.pF.E_commerce.modal.User;
import com.pF.E_commerce.response.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Result<Product> getProductById(int Id);
    Result<List<Category>> getAllCategories();
    Result<Page<Product>> getAllProducts(Pageable pageable);
//    Result<List<Object[]>> searchProductTitlesByKeywords(List<String> keywords);
    Result<Page<Product>> getProductsByCategory(int categoryId, Pageable pageable);
    Result<String> searchAndAddToCart(User user, List<String> keywords);
}
