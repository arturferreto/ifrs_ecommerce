package com.ifrs.ecommerce.repositories;

import com.ifrs.ecommerce.models.Cart;
import com.ifrs.ecommerce.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
    Optional<Cart> findByUser(User user);
}