package com.ifrs.ecommerce.repositories;

import com.ifrs.ecommerce.models.Order;
import com.ifrs.ecommerce.models.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    Iterable<Order> findAllByUser(User user);
}