package com.ifrs.ecommerce.services;

import com.ifrs.ecommerce.dtos.ProductFeatureDto;
import com.ifrs.ecommerce.models.Product;
import com.ifrs.ecommerce.models.ProductFeature;
import com.ifrs.ecommerce.repositories.ProductFeatureRepository;
import com.ifrs.ecommerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductFeatureService {
    private final ProductFeatureRepository productFeatureRepository;
    private final ProductRepository productRepository;
    private final CacheDataService cacheDataService;

    public ProductFeatureService(
            ProductFeatureRepository productFeatureRepository,
            ProductRepository productRepository,
            CacheDataService cacheDataService
    ) {
        this.productFeatureRepository = productFeatureRepository;
        this.productRepository = productRepository;
        this.cacheDataService = cacheDataService;
    }

    private Product checkProductExists(Integer productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<ProductFeature> all(Integer productId) {
        Product product = checkProductExists(productId);

        List<ProductFeature> features = new ArrayList<>();
        productFeatureRepository.findAllByProduct(product).forEach(feature -> features.add((ProductFeature) feature));

        return features;
    }

    public ProductFeature one(Integer id) {
        return productFeatureRepository.findById(id).orElse(null);
    }

    public ProductFeature store(Integer productId, ProductFeatureDto dto) {
        Product product = checkProductExists(productId);

        ProductFeature feature = new ProductFeature();
        feature.setProduct(product);
        feature.setName(dto.name());
        feature.setQuantity(dto.quantity());
        productFeatureRepository.save(feature);

        cacheDataService.clearCache();

        return feature;
    }

    public ProductFeature update(Integer productId, Integer id, ProductFeatureDto dto) {
        Product product = checkProductExists(productId);

        ProductFeature feature = productFeatureRepository.findById(id).orElse(null);

        if (feature == null) {
            return null;
        }

        feature.setName(dto.name());
        feature.setQuantity(dto.quantity());
        productFeatureRepository.save(feature);

        cacheDataService.clearCache();

        return feature;
    }

    public boolean delete(Integer productId, Integer id) {
        Product product = checkProductExists(productId);

        ProductFeature feature = productFeatureRepository.findById(id).orElse(null);

        if (feature == null) {
            return false;
        }

        productFeatureRepository.delete(feature);

        cacheDataService.clearCache();

        return true;
    }

    public void updateStock(ProductFeature feature, Integer quantity) {
        feature.setQuantity(feature.getQuantity() - quantity);
        productFeatureRepository.save(feature);
    }
}
