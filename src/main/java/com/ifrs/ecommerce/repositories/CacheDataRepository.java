package com.ifrs.ecommerce.repositories;

import com.ifrs.ecommerce.models.core.CacheData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CacheDataRepository extends CrudRepository<CacheData, String> {
    // ...
}