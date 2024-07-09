package com.ifrs.ecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ifrs.ecommerce.models.Product;
import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/public/products")
@RestController
public class ProductPublicController {
    private final ProductService productService;

    public ProductPublicController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<DefaultResponse> publicProducts() throws InterruptedException, JsonProcessingException {
        List<Product> products = productService.publicAllProducts();

        return DefaultResponse.build(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<DefaultResponse> publicProduct(@PathVariable Integer id) throws InterruptedException, JsonProcessingException {
        Product product = productService.publicProduct(id);

        return DefaultResponse.build(product);
    }
}
