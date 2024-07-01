package com.ifrs.ecommerce.repositories;

import com.ifrs.ecommerce.models.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
    // ...
}
