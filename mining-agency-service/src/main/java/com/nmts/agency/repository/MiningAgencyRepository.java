package com.nmts.agency.repository;

import com.nmts.agency.entity.MiningAgency;
import com.nmts.agency.entity.OperationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MiningAgencyRepository extends JpaRepository<MiningAgency, UUID> {
    Optional<MiningAgency> findByOwnerId(UUID ownerId);
    Page<MiningAgency> findByOperationStatus(OperationStatus status, Pageable pageable);
}
