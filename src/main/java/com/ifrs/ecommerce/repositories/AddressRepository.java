package com.ifrs.ecommerce.repositories;

import com.ifrs.ecommerce.models.Address;
import com.ifrs.ecommerce.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    Iterable<Address> findAllByUser(User user);
}
