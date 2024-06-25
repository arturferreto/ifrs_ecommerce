package com.ifrs.ecommerce.controllers;

import com.ifrs.ecommerce.models.Product;
import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<DefaultResponse> all() {
        List<Product> products = productService.all();

        return DefaultResponse.build(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> one(@PathVariable Integer id) {
        Product product = productService.one(id);

        if (product == null) {
            return DefaultResponse.build("Product not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(product);
    }
}
