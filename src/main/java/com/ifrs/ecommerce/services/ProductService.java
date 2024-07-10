package com.ifrs.ecommerce.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifrs.ecommerce.dtos.ProductDto;
import com.ifrs.ecommerce.models.Product;
import com.ifrs.ecommerce.models.core.CacheData;
import com.ifrs.ecommerce.repositories.ProductPhotoRepository;
import com.ifrs.ecommerce.repositories.ProductFeatureRepository;
import com.ifrs.ecommerce.repositories.ProductRepository;
import com.ifrs.ecommerce.repositories.core.CacheDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductFeatureRepository productFeatureRepository;
    private final ProductPhotoRepository productPhotoRepository;
    private final CacheDataRepository cacheDataRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(
            ProductRepository productRepository,
            ProductFeatureRepository productFeatureRepository,
            ProductPhotoRepository productPhotoRepository,
            CacheDataRepository cacheDataRepository,
            ObjectMapper objectMapper
    ) {
        this.productRepository = productRepository;
        this.productFeatureRepository = productFeatureRepository;
        this.productPhotoRepository = productPhotoRepository;
        this.cacheDataRepository = cacheDataRepository;
        this.objectMapper = objectMapper;
    }

    public List<Product> all() {
        List<Product> products = new ArrayList<>();

        productRepository.findAll().forEach(products::add);

        return products;
    }

    public Product one(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product store(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setDescription(dto.description());
        product.setFavoritePhotoUrl(dto.favoritePhotoUrl());
        productRepository.save(product);

        cacheDataRepository.deleteAll();

        return product;
    }

    public Product update(Integer id, ProductDto dto) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return null;
        }

        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setDescription(dto.description());
        product.setFavoritePhotoUrl(dto.favoritePhotoUrl());
        productRepository.save(product);

        cacheDataRepository.deleteAll();

        return product;
    }

    public boolean delete(Integer id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return false;
        }

        productRepository.delete(product);

        cacheDataRepository.deleteAll();

        return true;
    }

    public List<Product> publicAllProducts() throws InterruptedException, JsonProcessingException {
        Optional<CacheData> optionalCacheData = cacheDataRepository.findById("allProducts");

        // Cache hit
        if (optionalCacheData.isPresent()) {
            String productAsString = optionalCacheData.get().getValue();
            TypeReference<List<Product>> mapType = new TypeReference<>() {};

            return objectMapper.readValue(productAsString, mapType);
        }

        // Cache miss
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);

        String productsAsJsonString = objectMapper.writeValueAsString(products);
        CacheData cacheData = new CacheData("allProducts", productsAsJsonString);
        cacheDataRepository.save(cacheData);

        return products;
    }

    public Product publicProduct(Integer id) throws InterruptedException, JsonProcessingException {
        Optional<CacheData> optionalCacheData = cacheDataRepository.findById("product" + id);

        // Cache hit
        if (optionalCacheData.isPresent()) {
            String productAsString = optionalCacheData.get().getValue();
            return objectMapper.readValue(productAsString, Product.class);
        }

        // Cache miss
        Product product = productRepository.findById(id).orElse(null);

        if (product != null) {
            Iterable<Object> photos = productPhotoRepository.findByProductId(product.getId());
            product.setPhotos(photos);

            Iterable<Object> features = productFeatureRepository.findByProductId(product.getId());
            product.setFeatures(features);
        }

        if (product == null) {
            return null;
        }

        String productAsJsonString = objectMapper.writeValueAsString(product);
        CacheData cacheData = new CacheData("product" + id, productAsJsonString);
        cacheDataRepository.save(cacheData);

        return product;
    }

    public void clearCache() {
        cacheDataRepository.deleteAll();
    }
}
