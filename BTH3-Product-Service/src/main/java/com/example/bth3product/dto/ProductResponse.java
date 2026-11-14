package com.example.bth3product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.example.bth3product.entity.Product;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(), product.getName(), product.getDescription(),
                product.getPrice(), product.getQuantity(), product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
