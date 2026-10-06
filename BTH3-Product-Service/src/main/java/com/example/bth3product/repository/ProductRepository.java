package com.example.bth3product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bth3product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
