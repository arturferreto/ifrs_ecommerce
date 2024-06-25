package com.ifrs.ecommerce.repositories;

import com.ifrs.ecommerce.models.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
    // ...
}