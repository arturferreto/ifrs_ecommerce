package com.ifrs.ecommerce.repositories;

import com.ifrs.ecommerce.models.Discount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends CrudRepository<Discount, Integer> {
    // ...
}