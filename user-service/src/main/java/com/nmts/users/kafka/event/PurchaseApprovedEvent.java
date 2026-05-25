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
public class PurchaseApprovedEvent {
    private UUID requestId;
    private UUID customerId;
    private UUID agencyId;
    private String agencyName; // Added this as it's needed for history
    private String metalName;
    private Double requestedQtyTons;
    private BigDecimal pricePerTon; // Added this
    private BigDecimal totalEstimatedValue;
    private LocalDateTime approvedAt;
}