package com.ifrs.ecommerce.services;

import com.ifrs.ecommerce.models.Product;
import com.ifrs.ecommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> all() {
        List<Product> products = new ArrayList<>();

        productRepository.findAll().forEach(products::add);

        return products;
    }

    public Product one(Integer id) {
        return productRepository.findById(id).orElse(null);
    }
}
