package com.ifrs.ecommerce.repositories;

import com.ifrs.ecommerce.models.Product;
import com.ifrs.ecommerce.models.ProductFeature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFeatureRepository extends CrudRepository<ProductFeature, Integer> {
    Iterable<Object> findAllByProduct(Product product);
}
