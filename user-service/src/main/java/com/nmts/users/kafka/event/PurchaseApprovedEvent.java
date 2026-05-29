package com.nmts.users.kafka.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class PurchaseApprovedEvent {
    private UUID requestId;
    private UUID customerId;
    private UUID agencyId;
    private String agencyName;
    private String metalName;
    private Double requestedQtyTons;
    private BigDecimal pricePerTon;
    private BigDecimal totalEstimatedValue;
    private LocalDateTime approvedAt;

    public PurchaseApprovedEvent() {
    }

    public PurchaseApprovedEvent(UUID requestId, UUID customerId, UUID agencyId, String agencyName, String metalName, Double requestedQtyTons, BigDecimal pricePerTon, BigDecimal totalEstimatedValue, LocalDateTime approvedAt) {
        this.requestId = requestId;
        this.customerId = customerId;
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.metalName = metalName;
        this.requestedQtyTons = requestedQtyTons;
        this.pricePerTon = pricePerTon;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseApprovedEvent that = (PurchaseApprovedEvent) o;
        return Objects.equals(requestId, that.requestId) && Objects.equals(customerId, that.customerId) && Objects.equals(agencyId, that.agencyId) && Objects.equals(agencyName, that.agencyName) && Objects.equals(metalName, that.metalName) && Objects.equals(requestedQtyTons, that.requestedQtyTons) && Objects.equals(pricePerTon, that.pricePerTon) && Objects.equals(totalEstimatedValue, that.totalEstimatedValue) && Objects.equals(approvedAt, that.approvedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, customerId, agencyId, agencyName, metalName, requestedQtyTons, pricePerTon, totalEstimatedValue, approvedAt);
    }

    @Override
    public String toString() {
        return "PurchaseApprovedEvent{" +
                "requestId=" + requestId +
                ", customerId=" + customerId +
                ", agencyId=" + agencyId +
                ", agencyName='" + agencyName + '\'' +
                ", metalName='" + metalName + '\'' +
                ", requestedQtyTons=" + requestedQtyTons +
                ", pricePerTon=" + pricePerTon +
                ", totalEstimatedValue=" + totalEstimatedValue +
                ", approvedAt=" + approvedAt +
                '}';
    }

    public static PurchaseApprovedEventBuilder builder() {
        return new PurchaseApprovedEventBuilder();
    }

    public static class PurchaseApprovedEventBuilder {
        private UUID requestId;
        private UUID customerId;
        private UUID agencyId;
        private String agencyName;
        private String metalName;
        private Double requestedQtyTons;
        private BigDecimal pricePerTon;
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

        public PurchaseApprovedEventBuilder agencyName(String agencyName) {
            this.agencyName = agencyName;
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

        public PurchaseApprovedEventBuilder pricePerTon(BigDecimal pricePerTon) {
            this.pricePerTon = pricePerTon;
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
            return new PurchaseApprovedEvent(requestId, customerId, agencyId, agencyName, metalName, requestedQtyTons, pricePerTon, totalEstimatedValue, approvedAt);
        }

        @Override
        public String toString() {
            return "PurchaseApprovedEventBuilder{" +
                    "requestId=" + requestId +
                    ", customerId=" + customerId +
                    ", agencyId=" + agencyId +
                    ", agencyName='" + agencyName + '\'' +
                    ", metalName='" + metalName + '\'' +
                    ", requestedQtyTons=" + requestedQtyTons +
                    ", pricePerTon=" + pricePerTon +
                    ", totalEstimatedValue=" + totalEstimatedValue +
                    ", approvedAt=" + approvedAt +
                    '}';
        }
    }
}
