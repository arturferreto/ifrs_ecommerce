package com.ifrs.ecommerce.services;

import com.ifrs.ecommerce.models.core.CacheData;
import com.ifrs.ecommerce.repositories.core.CacheDataRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CacheDataService {
    private final CacheDataRepository cacheDataRepository;

    public CacheDataService(
        CacheDataRepository cacheDataRepository
    ) {
        this.cacheDataRepository = cacheDataRepository;
    }

    public void clearCache() {
        cacheDataRepository.deleteAll();
    }

    public void save(CacheData cacheData) {
        cacheDataRepository.save(cacheData);
    }

    public Optional<CacheData> findById(String string) {
        return cacheDataRepository.findById(string);
    }
}
