package com.pF.E_commerce.repository;

import com.pF.E_commerce.modal.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> , JpaSpecificationExecutor<Product> {
    @Query(value = "SELECT DISTINCT p.id, p.title FROM product p " +
            "JOIN category c ON p.category_id = c.id " +
            "WHERE LOWER(p.title) REGEXP :pattern", nativeQuery = true)
    List<Object[]> searchProductIdAndTitleByKeywords(@Param("pattern") String pattern);

    @Query("SELECT p FROM Product p WHERE p.quantity > 0")
    Page<Product> findAll(Pageable pageable);

    Product findById(Number Id);

    @Query("SELECT p FROM Product p WHERE p.category.id= :categoryId")
    Page<Product> findByCategory(Number categoryId, Pageable pageable);
}
