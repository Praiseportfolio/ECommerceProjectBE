package com.pF.E_commerce.service.impl;

import com.pF.E_commerce.modal.Cart;
import com.pF.E_commerce.modal.Category;
import com.pF.E_commerce.modal.Product;
import com.pF.E_commerce.modal.User;
import com.pF.E_commerce.repository.CategoryRepository;
import com.pF.E_commerce.repository.ProductRepository;
import com.pF.E_commerce.response.Result;
import com.pF.E_commerce.service.CartService;
import com.pF.E_commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Result<String> searchAndAddToCart(User user, List<String> keywords) {
        Result<String> result = new Result<>();
        try {
            if (keywords == null || keywords.isEmpty()) {
                result.setMessage("Please provide a shopping list");
                return result;
            }

            String pattern = String.join("|", keywords);

            List<Object[]> matching = productRepository.searchProductIdAndTitleByKeywords(pattern);

            List<Long> productIds = matching.stream()
                    .map(obj -> ((Number) obj[0]).longValue())
                    .toList();

            List<Product> products = productRepository.findAllById(productIds);

            List<Cart> addedCarts = cartService.addAllToCart(user, products);

            result.setMessage("Items successfully added to cart");
            return result;

        } catch (Exception e) {
            log.error("Error during search and add to cart: ", e);
            result.setStatus(false);
            result.setMessage("An error occurred while adding products to cart");
            return result;
        }
    }

    @Override
    public Result<List<Category>> getAllCategories() {
        Result<List<Category>> result = new Result<>();
        try{
            List<Category> categories = categoryRepository.findAll();
            if (categories == null){
                result.setMessage("No categories found");
                return result;
            }
            result.setData(categories);
            result.setMessage("Categories successfully fetched");
            return result;
        } catch (Exception e) {
            log.error("e: ", e);
            result.setStatus(false);
            result.setMessage("An error occurred");
            return result;
        }
    }

    @Override
    public Result<Product> getProductById(int Id) {
        Result<Product> result = new Result<>();
        try{
            Product product = productRepository.findById(Id);
            if (product == null){
                result.setMessage("No product found");
                return result;
            }
            result.setData(product);
            result.setMessage("Product successfully fetched");
            return result;
        } catch (Exception e) {
            log.error("e: ", e);
            result.setStatus(false);
            result.setMessage("An error occurred");
            return result;
        }
    }

    @Override
    public Result<Page<Product>> getAllProducts(Pageable pageable) {
        Result<Page<Product>> result = new Result<>();
        try {
            Page<Product> products = productRepository.findAll(pageable);
            if (products.isEmpty()) {
                result.setMessage("No products found");
                return result;
            }
            result.setData(products);
            result.setMessage("Products successfully fetched");
            return result;
        } catch (Exception e) {
            log.error("e: ", e);
            result.setStatus(false);
            result.setMessage("An error occurred");
            return result;
        }
    }

    @Override
    public Result<Page<Product>> getProductsByCategory(int categoryId, Pageable pageable) {
        Result<Page<Product>> result = new Result<>();
        try {
            Page<Product> products = productRepository.findByCategory(categoryId, pageable);
            if (products.isEmpty()) {
                result.setMessage("No products found");
                return result;
            }
            result.setData(products);
            result.setMessage("Products successfully fetched");
            return result;
        } catch (Exception e) {
            log.error("e: ", e);
            result.setStatus(false);
            result.setMessage("An error occurred");
            return result;
        }
    }
}
