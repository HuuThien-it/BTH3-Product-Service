package com.example.bth3product.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Khong tim thay san pham voi id = " + id);
    }
}
