package com.ifrs.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifrs.ecommerce.dtos.ProductPhotoDto;
import com.ifrs.ecommerce.models.ProductPhoto;
import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.services.ProductPhotoService;

import jakarta.validation.Valid;

import java.util.List;

@RequestMapping("/products/{productId}/photos")
@RestController
public class ProductPhotoController {
    private final ProductPhotoService productPhotoService;

    public ProductPhotoController(ProductPhotoService productPhotoService) {
        this.productPhotoService = productPhotoService;
    }

    @GetMapping
    public ResponseEntity<DefaultResponse> all(@PathVariable Integer productId) {
        List<ProductPhoto> photos = productPhotoService.all(productId);
        
        return DefaultResponse.build(photos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> one(@PathVariable Integer productId, @PathVariable Integer id) {
        ProductPhoto photo = productPhotoService.one(productId, id);

        if (photo == null) {
            return DefaultResponse.build("Photo not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(photo);
    }

    @PostMapping
    public ResponseEntity<DefaultResponse> store(@PathVariable Integer productId, @RequestBody @Valid ProductPhotoDto productPhotoDto) {
        ProductPhoto photo = productPhotoService.store(productId, productPhotoDto);

        return DefaultResponse.build(photo, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse> update(@PathVariable Integer productId, @PathVariable Integer id, @RequestBody @Valid ProductPhotoDto productPhotoDto) {
        ProductPhoto photo = productPhotoService.update(productId, id, productPhotoDto);

        if (photo == null) {
            return DefaultResponse.build("Photo not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(photo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable Integer productId, @PathVariable Integer id) {
        boolean deleted = productPhotoService.delete(productId, id);

        if (!deleted) {
            return DefaultResponse.build("Photo not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build("Photo deleted");
    }
}
