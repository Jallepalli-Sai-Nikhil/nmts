package com.nmts.users.kafka.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class PurchaseRejectedEvent {
    private UUID requestId;
    private UUID customerId;
    private UUID agencyId;
    private String agencyName;
    private String metalName;
    private Double requestedQtyTons;
    private BigDecimal pricePerTon;
    private BigDecimal totalValue;
    private String rejectionReason;
    private LocalDateTime rejectedAt;

    public PurchaseRejectedEvent() {
    }

    public PurchaseRejectedEvent(UUID requestId, UUID customerId, UUID agencyId, String agencyName, String metalName, Double requestedQtyTons, BigDecimal pricePerTon, BigDecimal totalValue, String rejectionReason, LocalDateTime rejectedAt) {
        this.requestId = requestId;
        this.customerId = customerId;
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.metalName = metalName;
        this.requestedQtyTons = requestedQtyTons;
        this.pricePerTon = pricePerTon;
        this.totalValue = totalValue;
        this.rejectionReason = rejectionReason;
        this.rejectedAt = rejectedAt;
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

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
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

    public BigDecimal getPricePerTon() {
        return pricePerTon;
    }

    public void setPricePerTon(BigDecimal pricePerTon) {
        this.pricePerTon = pricePerTon;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseRejectedEvent that = (PurchaseRejectedEvent) o;
        return Objects.equals(requestId, that.requestId) && Objects.equals(customerId, that.customerId) && Objects.equals(agencyId, that.agencyId) && Objects.equals(agencyName, that.agencyName) && Objects.equals(metalName, that.metalName) && Objects.equals(requestedQtyTons, that.requestedQtyTons) && Objects.equals(pricePerTon, that.pricePerTon) && Objects.equals(totalValue, that.totalValue) && Objects.equals(rejectionReason, that.rejectionReason) && Objects.equals(rejectedAt, that.rejectedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, customerId, agencyId, agencyName, metalName, requestedQtyTons, pricePerTon, totalValue, rejectionReason, rejectedAt);
    }

    @Override
    public String toString() {
        return "PurchaseRejectedEvent{" +
                "requestId=" + requestId +
                ", customerId=" + customerId +
                ", agencyId=" + agencyId +
                ", agencyName='" + agencyName + '\'' +
                ", metalName='" + metalName + '\'' +
                ", requestedQtyTons=" + requestedQtyTons +
                ", pricePerTon=" + pricePerTon +
                ", totalValue=" + totalValue +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", rejectedAt=" + rejectedAt +
                '}';
    }

    public static PurchaseRejectedEventBuilder builder() {
        return new PurchaseRejectedEventBuilder();
    }

    public static class PurchaseRejectedEventBuilder {
        private UUID requestId;
        private UUID customerId;
        private UUID agencyId;
        private String agencyName;
        private String metalName;
        private Double requestedQtyTons;
        private BigDecimal pricePerTon;
        private BigDecimal totalValue;
        private String rejectionReason;
        private LocalDateTime rejectedAt;

        PurchaseRejectedEventBuilder() {
        }

        public PurchaseRejectedEventBuilder requestId(UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public PurchaseRejectedEventBuilder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public PurchaseRejectedEventBuilder agencyId(UUID agencyId) {
            this.agencyId = agencyId;
            return this;
        }

        public PurchaseRejectedEventBuilder agencyName(String agencyName) {
            this.agencyName = agencyName;
            return this;
        }

        public PurchaseRejectedEventBuilder metalName(String metalName) {
            this.metalName = metalName;
            return this;
        }

        public PurchaseRejectedEventBuilder requestedQtyTons(Double requestedQtyTons) {
            this.requestedQtyTons = requestedQtyTons;
            return this;
        }

        public PurchaseRejectedEventBuilder pricePerTon(BigDecimal pricePerTon) {
            this.pricePerTon = pricePerTon;
            return this;
        }

        public PurchaseRejectedEventBuilder totalValue(BigDecimal totalValue) {
            this.totalValue = totalValue;
            return this;
        }

        public PurchaseRejectedEventBuilder rejectionReason(String rejectionReason) {
            this.rejectionReason = rejectionReason;
            return this;
        }

        public PurchaseRejectedEventBuilder rejectedAt(LocalDateTime rejectedAt) {
            this.rejectedAt = rejectedAt;
            return this;
        }

        public PurchaseRejectedEvent build() {
            return new PurchaseRejectedEvent(requestId, customerId, agencyId, agencyName, metalName, requestedQtyTons, pricePerTon, totalValue, rejectionReason, rejectedAt);
        }

        @Override
        public String toString() {
            return "PurchaseRejectedEventBuilder{" +
                    "requestId=" + requestId +
                    ", customerId=" + customerId +
                    ", agencyId=" + agencyId +
                    ", agencyName='" + agencyName + '\'' +
                    ", metalName='" + metalName + '\'' +
                    ", requestedQtyTons=" + requestedQtyTons +
                    ", pricePerTon=" + pricePerTon +
                    ", totalValue=" + totalValue +
                    ", rejectionReason='" + rejectionReason + '\'' +
                    ", rejectedAt=" + rejectedAt +
                    '}';
        }
    }
}
