package com.pF.E_commerce.repository;


import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pF.E_commerce.modal.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
//    Category findByCategoryId(String categoryId);
//    List<Category>findByLevel(Integer level);
    List<Category> findAll();
}

