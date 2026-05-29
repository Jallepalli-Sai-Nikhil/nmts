package com.nmts.agency.kafka.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PurchaseApprovedEvent {
    private UUID requestId;
    private UUID customerId;
    private UUID agencyId;
    private String metalName;
    private Double requestedQtyTons;
    private BigDecimal totalEstimatedValue;
    private LocalDateTime approvedAt;

    public PurchaseApprovedEvent() {
    }

    public PurchaseApprovedEvent(UUID requestId, UUID customerId, UUID agencyId, String metalName, Double requestedQtyTons, BigDecimal totalEstimatedValue, LocalDateTime approvedAt) {
        this.requestId = requestId;
        this.customerId = customerId;
        this.agencyId = agencyId;
        this.metalName = metalName;
        this.requestedQtyTons = requestedQtyTons;
        this.totalEstimatedValue = totalEstimatedValue;
        this.approvedAt = approvedAt;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(UUID agencyId) {
        this.agencyId = agencyId;
    }

    public String getMetalName() {
        return metalName;
    }

    public void setMetalName(String metalName) {
        this.metalName = metalName;
    }

    public Double getRequestedQtyTons() {
        return requestedQtyTons;
    }

    public void setRequestedQtyTons(Double requestedQtyTons) {
        this.requestedQtyTons = requestedQtyTons;
    }

    public BigDecimal getTotalEstimatedValue() {
        return totalEstimatedValue;
    }

    public void setTotalEstimatedValue(BigDecimal totalEstimatedValue) {
        this.totalEstimatedValue = totalEstimatedValue;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public static PurchaseApprovedEventBuilder builder() {
        return new PurchaseApprovedEventBuilder();
    }

    public static class PurchaseApprovedEventBuilder {
        private UUID requestId;
        private UUID customerId;
        private UUID agencyId;
        private String metalName;
        private Double requestedQtyTons;
        private BigDecimal totalEstimatedValue;
        private LocalDateTime approvedAt;

        PurchaseApprovedEventBuilder() {
        }

        public PurchaseApprovedEventBuilder requestId(UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public PurchaseApprovedEventBuilder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public PurchaseApprovedEventBuilder agencyId(UUID agencyId) {
            this.agencyId = agencyId;
            return this;
        }

        public PurchaseApprovedEventBuilder metalName(String metalName) {
            this.metalName = metalName;
            return this;
        }

        public PurchaseApprovedEventBuilder requestedQtyTons(Double requestedQtyTons) {
            this.requestedQtyTons = requestedQtyTons;
            return this;
        }

        public PurchaseApprovedEventBuilder totalEstimatedValue(BigDecimal totalEstimatedValue) {
            this.totalEstimatedValue = totalEstimatedValue;
            return this;
        }

        public PurchaseApprovedEventBuilder approvedAt(LocalDateTime approvedAt) {
            this.approvedAt = approvedAt;
            return this;
        }

        public PurchaseApprovedEvent build() {
            return new PurchaseApprovedEvent(requestId, customerId, agencyId, metalName, requestedQtyTons, totalEstimatedValue, approvedAt);
        }

        @Override
        public String toString() {
            return "PurchaseApprovedEventBuilder(requestId=" + this.requestId + ", customerId=" + this.customerId + ", agencyId=" + this.agencyId + ", metalName=" + this.metalName + ", requestedQtyTons=" + this.requestedQtyTons + ", totalEstimatedValue=" + this.totalEstimatedValue + ", approvedAt=" + this.approvedAt + ")";
        }
    }
}
