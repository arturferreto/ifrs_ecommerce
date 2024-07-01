package com.ifrs.ecommerce.controllers;

import com.ifrs.ecommerce.dtos.ProductStoreDto;
import com.ifrs.ecommerce.dtos.ProductUpdateDto;
import com.ifrs.ecommerce.models.Product;
import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<DefaultResponse> store(@RequestBody @Valid ProductStoreDto productStoreDto) {
        Product product = productService.store(productStoreDto);

        return DefaultResponse.build(product, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse> update(@PathVariable Integer id, @RequestBody @Valid ProductUpdateDto productUpdateDto) {
        Product product = productService.update(id, productUpdateDto);

        if (product == null) {
            return DefaultResponse.build("Product not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable Integer id) {
        boolean deleted = productService.delete(id);

        if (!deleted) {
            return DefaultResponse.build("Product not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build("Product deleted");
    }
}
