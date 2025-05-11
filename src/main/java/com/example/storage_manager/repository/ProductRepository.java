package com.example.storage_manager.repository;

import com.example.storage_manager.entity.Category;
import com.example.storage_manager.entity.Product;
import com.example.storage_manager.entity.Sex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
    List<Product> findBySex(Sex sex);
    List<Product> findByQuantity(int minAlert);
}
