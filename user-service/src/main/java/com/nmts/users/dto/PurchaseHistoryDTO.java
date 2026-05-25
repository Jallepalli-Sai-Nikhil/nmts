package com.nmts.users.dto;

import com.nmts.users.entity.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseHistoryDTO {
    private UUID id;
    private String agencyName;
    private String metalName;
    private Double requestedQtyTons;
    private BigDecimal pricePerTon;
    private BigDecimal totalValue;
    private PurchaseStatus status;
    private LocalDateTime processedAt;
}
