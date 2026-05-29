package com.nmts.search.repository;

import com.nmts.search.entity.MetalCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface MetalCatalogRepository extends JpaRepository<MetalCatalog, UUID> {
    List<MetalCatalog> findByMetalNameContainingIgnoreCase(String name);
}
