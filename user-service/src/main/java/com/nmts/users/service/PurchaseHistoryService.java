package com.nmts.users.service;


import com.nmts.users.dto.PurchaseHistoryDTO;
import com.nmts.users.entity.PurchaseHistory;
import com.nmts.users.repository.PurchaseHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PurchaseHistoryService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseHistoryService.class);

    private final PurchaseHistoryRepository purchaseHistoryRepository;

    public PurchaseHistoryService(PurchaseHistoryRepository purchaseHistoryRepository) {
        this.purchaseHistoryRepository = purchaseHistoryRepository;
    }

    public List<PurchaseHistoryDTO> getPurchaseHistory(UUID customerId) {
        log.info("Fetching purchase history for customerId: {}", customerId);
        return purchaseHistoryRepository.findByCustomerIdOrderByProcessedAtDesc(customerId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void savePurchaseHistory(PurchaseHistory history) {
        log.info("Saving purchase history for customerId: {}", history.getCustomerId());
        purchaseHistoryRepository.save(history);
    }

    private PurchaseHistoryDTO mapToDTO(PurchaseHistory history) {
        return PurchaseHistoryDTO.builder()
                .id(history.getId())
                .agencyName(history.getAgencyName())
                .metalName(history.getMetalName())
                .requestedQtyTons(history.getRequestedQtyTons())
                .pricePerTon(history.getPricePerTon())
                .totalValue(history.getTotalValue())
                .status(history.getStatus())
                .processedAt(history.getProcessedAt())
                .build();
    }
}
