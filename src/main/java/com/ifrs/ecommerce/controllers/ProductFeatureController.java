package com.ifrs.ecommerce.controllers;

import com.ifrs.ecommerce.dtos.ProductFeatureDto;
import com.ifrs.ecommerce.models.ProductFeature;
import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.services.ProductFeatureService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products/{productId}/features")
@RestController
public class ProductFeatureController {
    private final ProductFeatureService productFeatureService;

    public ProductFeatureController(ProductFeatureService productFeatureService) {
        this.productFeatureService = productFeatureService;
    }

    @GetMapping
    public ResponseEntity<DefaultResponse> all(@PathVariable Integer productId) {
        List<ProductFeature> features = productFeatureService.all(productId);

        return DefaultResponse.build(features);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> one(@PathVariable Integer productId, @PathVariable Integer id) {
        ProductFeature feature = productFeatureService.one(id);

        if (feature == null) {
            return DefaultResponse.build("Feature not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(feature);
    }

    @PostMapping
    public ResponseEntity<DefaultResponse> store(@PathVariable Integer productId, @RequestBody @Valid ProductFeatureDto productFeatureDto) {
        ProductFeature feature = productFeatureService.store(productId, productFeatureDto);

        return DefaultResponse.build(feature, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse> update(@PathVariable Integer productId, @PathVariable Integer id, @RequestBody @Valid ProductFeatureDto productFeatureDto) {
        ProductFeature feature = productFeatureService.update(productId, id, productFeatureDto);

        if (feature == null) {
            return DefaultResponse.build("Feature not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build(feature);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable Integer productId, @PathVariable Integer id) {
        boolean deleted = productFeatureService.delete(productId, id);

        if (!deleted) {
            return DefaultResponse.build("Feature not found", HttpStatus.NOT_FOUND);
        }

        return DefaultResponse.build("Feature deleted");
    }
}
