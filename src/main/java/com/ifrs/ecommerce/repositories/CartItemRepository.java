package com.ifrs.ecommerce.repositories;

import com.ifrs.ecommerce.models.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
    // ...
}
