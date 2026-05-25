package com.nmts.users.repository;

import com.nmts.users.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, UUID> {
    List<PurchaseHistory> findByCustomerIdOrderByProcessedAtDesc(UUID customerId);
}
