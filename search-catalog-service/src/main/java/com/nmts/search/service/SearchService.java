package com.nmts.search.service;

import com.nmts.search.entity.MetalCatalog;
import com.nmts.search.repository.MetalCatalogRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchService {

    private final MetalCatalogRepository catalogRepository;

    public SearchService(MetalCatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Cacheable(value = "metals", key = "#name")
    public List<MetalCatalog> searchByName(String name) {
        return catalogRepository.findByMetalNameContainingIgnoreCase(name);
    }
}
