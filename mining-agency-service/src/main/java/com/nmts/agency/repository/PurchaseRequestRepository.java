package com.nmts.agency.repository;

import com.nmts.agency.entity.PurchaseRequest;
import com.nmts.agency.entity.PurchaseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, UUID> {
    List<PurchaseRequest> findByCustomerIdOrderByCreatedAtDesc(UUID customerId);
    List<PurchaseRequest> findByAgencyIdAndStatus(UUID agencyId, PurchaseStatus status);
    Page<PurchaseRequest> findByAgencyId(UUID agencyId, Pageable pageable);
}
