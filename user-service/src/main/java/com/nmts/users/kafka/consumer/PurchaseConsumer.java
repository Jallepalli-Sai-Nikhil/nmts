package com.nmts.users.kafka.consumer;


import com.nmts.users.entity.PurchaseHistory;
import com.nmts.users.entity.PurchaseStatus;
import com.nmts.users.kafka.event.PurchaseApprovedEvent;
import com.nmts.users.kafka.event.PurchaseRejectedEvent;
import com.nmts.users.service.PurchaseHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PurchaseConsumer {

    private static final Logger log = LoggerFactory.getLogger(PurchaseConsumer.class);

    private final PurchaseHistoryService purchaseHistoryService;

    public PurchaseConsumer(PurchaseHistoryService purchaseHistoryService) {
        this.purchaseHistoryService = purchaseHistoryService;
    }

    @KafkaListener(topics = "purchase.approved", groupId = "nmts-group")
    public void consumeApproved(PurchaseApprovedEvent event) {
        log.info("Consuming purchase.approved event for requestId: {}", event.getRequestId());
        PurchaseHistory history = PurchaseHistory.builder()
                .id(UUID.randomUUID())
                .customerId(event.getCustomerId())
                .agencyId(event.getAgencyId())
                .agencyName(event.getAgencyName())
                .metalName(event.getMetalName())
                .requestedQtyTons(event.getRequestedQtyTons())
                .pricePerTon(event.getPricePerTon())
                .totalValue(event.getTotalEstimatedValue())
                .status(PurchaseStatus.APPROVED)
                .processedAt(event.getApprovedAt())
                .build();
        purchaseHistoryService.savePurchaseHistory(history);
    }

    @KafkaListener(topics = "purchase.rejected", groupId = "nmts-group")
    public void consumeRejected(PurchaseRejectedEvent event) {
        log.info("Consuming purchase.rejected event for requestId: {}", event.getRequestId());
        PurchaseHistory history = PurchaseHistory.builder()
                .id(UUID.randomUUID())
                .customerId(event.getCustomerId())
                .agencyId(event.getAgencyId())
                .agencyName(event.getAgencyName())
                .metalName(event.getMetalName())
                .requestedQtyTons(event.getRequestedQtyTons())
                .pricePerTon(event.getPricePerTon())
                .totalValue(event.getTotalValue())
                .status(PurchaseStatus.REJECTED)
                .processedAt(event.getRejectedAt())
                .build();
        purchaseHistoryService.savePurchaseHistory(history);
    }
}
