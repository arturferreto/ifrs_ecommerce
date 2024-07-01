package com.ifrs.ecommerce.repositories;

import com.ifrs.ecommerce.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    // ...
}
