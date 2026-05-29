package com.nmts.agency.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchase_request")
public class PurchaseRequest {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID listingId;

    @Column(nullable = false)
    private UUID agencyId;

    @Column(nullable = false)
    private UUID customerId;

    private String customerName;

    @Column(nullable = false)
    private Double requestedQtyTons;

    @Column(nullable = false)
    private BigDecimal pricePerTon;

    @Column(nullable = false)
    private BigDecimal totalEstimatedValue;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status = PurchaseStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String rejectionReason;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public PurchaseRequest() {
    }

    public PurchaseRequest(UUID id, UUID listingId, UUID agencyId, UUID customerId, String customerName, Double requestedQtyTons, BigDecimal pricePerTon, BigDecimal totalEstimatedValue, String message, PurchaseStatus status, String rejectionReason, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.listingId = listingId;
        this.agencyId = agencyId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.requestedQtyTons = requestedQtyTons;
        this.pricePerTon = pricePerTon;
        this.totalEstimatedValue = totalEstimatedValue;
        this.message = message;
        this.status = status;
        this.rejectionReason = rejectionReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getListingId() {
        return listingId;
    }

    public void setListingId(UUID listingId) {
        this.listingId = listingId;
    }

    public UUID getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(UUID agencyId) {
        this.agencyId = agencyId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseStatus status) {
        this.status = status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static PurchaseRequestBuilder builder() {
        return new PurchaseRequestBuilder();
    }

    public static class PurchaseRequestBuilder {
        private UUID id;
        private UUID listingId;
        private UUID agencyId;
        private UUID customerId;
        private String customerName;
        private Double requestedQtyTons;
        private BigDecimal pricePerTon;
        private BigDecimal totalEstimatedValue;
        private String message;
        private PurchaseStatus status = PurchaseStatus.PENDING;
        private String rejectionReason;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        PurchaseRequestBuilder() {
        }

        public PurchaseRequestBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public PurchaseRequestBuilder listingId(UUID listingId) {
            this.listingId = listingId;
            return this;
        }

        public PurchaseRequestBuilder agencyId(UUID agencyId) {
            this.agencyId = agencyId;
            return this;
        }

        public PurchaseRequestBuilder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public PurchaseRequestBuilder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public PurchaseRequestBuilder requestedQtyTons(Double requestedQtyTons) {
            this.requestedQtyTons = requestedQtyTons;
            return this;
        }

        public PurchaseRequestBuilder pricePerTon(BigDecimal pricePerTon) {
            this.pricePerTon = pricePerTon;
            return this;
        }

        public PurchaseRequestBuilder totalEstimatedValue(BigDecimal totalEstimatedValue) {
            this.totalEstimatedValue = totalEstimatedValue;
            return this;
        }

        public PurchaseRequestBuilder message(String message) {
            this.message = message;
            return this;
        }

        public PurchaseRequestBuilder status(PurchaseStatus status) {
            this.status = status;
            return this;
        }

        public PurchaseRequestBuilder rejectionReason(String rejectionReason) {
            this.rejectionReason = rejectionReason;
            return this;
        }

        public PurchaseRequestBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PurchaseRequestBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PurchaseRequest build() {
            return new PurchaseRequest(id, listingId, agencyId, customerId, customerName, requestedQtyTons, pricePerTon, totalEstimatedValue, message, status, rejectionReason, createdAt, updatedAt);
        }

        @Override
        public String toString() {
            return "PurchaseRequestBuilder(id=" + this.id + ", listingId=" + this.listingId + ", agencyId=" + this.agencyId + ", customerId=" + this.customerId + ", customerName=" + this.customerName + ", requestedQtyTons=" + this.requestedQtyTons + ", pricePerTon=" + this.pricePerTon + ", totalEstimatedValue=" + this.totalEstimatedValue + ", message=" + this.message + ", status=" + this.status + ", rejectionReason=" + this.rejectionReason + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
