package com.ifrs.ecommerce.repositories;

import com.ifrs.ecommerce.models.ProductPhoto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPhotoRepository extends CrudRepository<ProductPhoto, Integer> {
    // ...
}
