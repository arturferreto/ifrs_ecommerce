package com.ifrs.ecommerce.repositories;

import com.ifrs.ecommerce.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    // ...
}