package com.ifrs.ecommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ifrs.ecommerce.dtos.ProductPhotoDto;
import com.ifrs.ecommerce.models.Product;
import com.ifrs.ecommerce.models.ProductPhoto;
import com.ifrs.ecommerce.repositories.ProductPhotoRepository;
import com.ifrs.ecommerce.repositories.ProductRepository;

@Service
public class ProductPhotoService {
    private final ProductPhotoRepository productPhotoRepository;
    private final ProductRepository productRepository;

    public ProductPhotoService(
        ProductPhotoRepository productPhotoRepository,
        ProductRepository productRepository
    ) {
        this.productPhotoRepository = productPhotoRepository;
        this.productRepository = productRepository;
    }

    private Product checkProductExists(Integer productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<ProductPhoto> all(Integer productId) {
        Product product = checkProductExists(productId);

        List<ProductPhoto> photos = new ArrayList<>();
        productPhotoRepository.findAllByProduct(product).forEach(photo -> photos.add((ProductPhoto) photo));

        return photos;
    }

    public ProductPhoto one(Integer productId, Integer id) {
        Product product = checkProductExists(productId);

        return productPhotoRepository.findById(id).orElse(null);
    }

    public ProductPhoto store(Integer productId, ProductPhotoDto dto) {
        Product product = checkProductExists(productId);

        ProductPhoto photo = new ProductPhoto();
        photo.setPhotoUrl(dto.photoUrl());
        photo.setProduct(product);
        productPhotoRepository.save(photo);

        return photo;
    }

    public ProductPhoto update(Integer productId, Integer id, ProductPhotoDto dto) {
        Product product = checkProductExists(productId);

        ProductPhoto photo = productPhotoRepository.findById(id).orElse(null);

        if (photo == null){
            return null;
        }

        photo.setPhotoUrl(dto.photoUrl());
        productPhotoRepository.save(photo);

        return photo;
    }

    public boolean delete(Integer productId, Integer id) {
        Product product = checkProductExists(productId);

        ProductPhoto photo = productPhotoRepository.findById(id).orElse(null);

        if (photo == null) {
            return false;
        }

        productPhotoRepository.delete(photo);

        return true;
    }
}
