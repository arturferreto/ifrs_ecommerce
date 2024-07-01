package com.ifrs.ecommerce.services;

import com.ifrs.ecommerce.dtos.ProductStoreDto;
import com.ifrs.ecommerce.dtos.ProductUpdateDto;
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

    public Product store(ProductStoreDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setDescription(dto.description());
        productRepository.save(product);
        return product;
    }

    public Product update(Integer id, ProductUpdateDto dto) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return null;
        }

        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setDescription(dto.description());
        productRepository.save(product);

        return product;
    }

    public boolean delete(Integer id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return false;
        }

        productRepository.delete(product);

        return true;
    }
}
