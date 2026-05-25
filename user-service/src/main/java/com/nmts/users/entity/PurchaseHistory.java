package com.nmts.users.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseHistory {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID customerId;

    @Column(nullable = false)
    private UUID agencyId;

    private String agencyName;
    private String metalName;
    private Double requestedQtyTons;

    @Column(precision = 15, scale = 2)
    private BigDecimal pricePerTon;

    @Column(precision = 15, scale = 2)
    private BigDecimal totalValue;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    private LocalDateTime processedAt;
}
