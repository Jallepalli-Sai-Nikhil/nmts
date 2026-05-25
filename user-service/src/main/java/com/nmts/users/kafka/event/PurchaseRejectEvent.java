package com.nmts.users.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRejectedEvent {
    private UUID requestId;
    private UUID customerId;
    private UUID agencyId;
    private String agencyName; // For history
    private String metalName;
    private Double requestedQtyTons; // For history
    private BigDecimal pricePerTon; // For history
    private BigDecimal totalValue; // For history
    private String rejectionReason;
    private LocalDateTime rejectedAt;
}