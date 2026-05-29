package com.nmts.agency.repository;

import com.nmts.agency.entity.MetalListing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MetalListingRepository extends JpaRepository<MetalListing, UUID> {
    List<MetalListing> findByAgencyIdAndIsActiveTrue(UUID agencyId);

    @Query("SELECT ml FROM MetalListing ml WHERE LOWER(ml.metalName) LIKE LOWER(CONCAT('%', :metalName, '%')) AND ml.isActive = true")
    Page<MetalListing> findByMetalNameContainingIgnoreCaseAndIsActiveTrue(@Param("metalName") String metalName, Pageable pageable);

    @Query("SELECT ml FROM MetalListing ml WHERE ml.agencyId IN :agencyIds AND ml.isActive = true AND LOWER(ml.metalName) LIKE LOWER(CONCAT('%', :metalName, '%'))")
    Page<MetalListing> findByAgencyIdInAndIsActiveTrueAndMetalNameContainingIgnoreCase(@Param("agencyIds") List<UUID> agencyIds, @Param("metalName") String metalName, Pageable pageable);

    @Query("SELECT ml FROM MetalListing ml JOIN MiningAgency ma ON ml.agencyId = ma.id WHERE ml.isActive = true AND ma.operationStatus = 'ACTIVE' AND (:metalName IS NULL OR LOWER(ml.metalName) LIKE LOWER(CONCAT('%', :metalName, '%')))")
    Page<MetalListing> findActiveListingsFromActiveAgencies(@Param("metalName") String metalName, Pageable pageable);

    @Query("SELECT ml FROM MetalListing ml JOIN MiningAgency ma ON ml.agencyId = ma.id WHERE ml.isActive = true AND ma.operationStatus = 'ACTIVE' AND LOWER(ml.metalName) = LOWER(:metalName)")
    List<MetalListing> findActiveListingsByExactMetalNameFromActiveAgencies(@Param("metalName") String metalName);
}
