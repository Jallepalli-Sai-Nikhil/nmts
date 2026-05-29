package com.nmts.agency.kafka.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PurchaseRequestedEvent {
    private UUID requestId;
    private UUID customerId;
    private String customerName;
    private UUID agencyId;
    private String metalName;
    private Double requestedQtyTons;
    private BigDecimal totalEstimatedValue;
    private LocalDateTime timestamp;

    public PurchaseRequestedEvent() {
    }

    public PurchaseRequestedEvent(UUID requestId, UUID customerId, String customerName, UUID agencyId, String metalName, Double requestedQtyTons, BigDecimal totalEstimatedValue, LocalDateTime timestamp) {
        this.requestId = requestId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.agencyId = agencyId;
        this.metalName = metalName;
        this.requestedQtyTons = requestedQtyTons;
        this.totalEstimatedValue = totalEstimatedValue;
        this.timestamp = timestamp;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public static PurchaseRequestedEventBuilder builder() {
        return new PurchaseRequestedEventBuilder();
    }

    public static class PurchaseRequestedEventBuilder {
        private UUID requestId;
        private UUID customerId;
        private String customerName;
        private UUID agencyId;
        private String metalName;
        private Double requestedQtyTons;
        private BigDecimal totalEstimatedValue;
        private LocalDateTime timestamp;

        PurchaseRequestedEventBuilder() {
        }

        public PurchaseRequestedEventBuilder requestId(UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public PurchaseRequestedEventBuilder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public PurchaseRequestedEventBuilder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public PurchaseRequestedEventBuilder agencyId(UUID agencyId) {
            this.agencyId = agencyId;
            return this;
        }

        public PurchaseRequestedEventBuilder metalName(String metalName) {
            this.metalName = metalName;
            return this;
        }

        public PurchaseRequestedEventBuilder requestedQtyTons(Double requestedQtyTons) {
            this.requestedQtyTons = requestedQtyTons;
            return this;
        }

        public PurchaseRequestedEventBuilder totalEstimatedValue(BigDecimal totalEstimatedValue) {
            this.totalEstimatedValue = totalEstimatedValue;
            return this;
        }

        public PurchaseRequestedEventBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public PurchaseRequestedEvent build() {
            return new PurchaseRequestedEvent(requestId, customerId, customerName, agencyId, metalName, requestedQtyTons, totalEstimatedValue, timestamp);
        }

        @Override
        public String toString() {
            return "PurchaseRequestedEventBuilder(requestId=" + this.requestId + ", customerId=" + this.customerId + ", customerName=" + this.customerName + ", agencyId=" + this.agencyId + ", metalName=" + this.metalName + ", requestedQtyTons=" + this.requestedQtyTons + ", totalEstimatedValue=" + this.totalEstimatedValue + ", timestamp=" + this.timestamp + ")";
        }
    }
}
