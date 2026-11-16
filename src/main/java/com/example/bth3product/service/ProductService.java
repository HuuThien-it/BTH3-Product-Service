package com.example.bth3product.service;

import java.util.List;
import com.example.bth3product.dto.ProductRequest;
import com.example.bth3product.dto.ProductResponse;
import com.example.bth3product.entity.Product;
import com.example.bth3product.exception.ProductNotFoundException;
import com.example.bth3product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(ProductResponse::from).toList();
    }

    public ProductResponse findById(Long id) {
        return ProductResponse.from(getExisting(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = new Product(
                request.name(), request.description(), request.price(), request.quantity()
        );
        return ProductResponse.from(repository.saveAndFlush(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = getExisting(id);
        product.update(request.name(), request.description(), request.price(), request.quantity());
        return ProductResponse.from(repository.saveAndFlush(product));
    }

    @Transactional
    public void delete(Long id) {
        Product product = getExisting(id);
        repository.delete(product);
    }

    private Product getExisting(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
}
