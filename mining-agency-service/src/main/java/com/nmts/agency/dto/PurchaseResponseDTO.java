package com.nmts.agency.dto;

import com.nmts.agency.entity.PurchaseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class PurchaseResponseDTO {
    private UUID id;
    private String metalName;
    private String agencyName;
    private Double requestedQtyTons;
    private BigDecimal pricePerTon;
    private BigDecimal totalEstimatedValue;
    private PurchaseStatus status;
    private LocalDateTime createdAt;

    public PurchaseResponseDTO() {
    }

    public PurchaseResponseDTO(UUID id, String metalName, String agencyName, Double requestedQtyTons, BigDecimal pricePerTon, BigDecimal totalEstimatedValue, PurchaseStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.metalName = metalName;
        this.agencyName = agencyName;
        this.requestedQtyTons = requestedQtyTons;
        this.pricePerTon = pricePerTon;
        this.totalEstimatedValue = totalEstimatedValue;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMetalName() {
        return metalName;
    }

    public void setMetalName(String metalName) {
        this.metalName = metalName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
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

    public PurchaseStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseResponseDTO that = (PurchaseResponseDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(metalName, that.metalName) &&
                Objects.equals(agencyName, that.agencyName) &&
                Objects.equals(requestedQtyTons, that.requestedQtyTons) &&
                Objects.equals(pricePerTon, that.pricePerTon) &&
                Objects.equals(totalEstimatedValue, that.totalEstimatedValue) &&
                status == that.status &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, metalName, agencyName, requestedQtyTons, pricePerTon, totalEstimatedValue, status, createdAt);
    }

    @Override
    public String toString() {
        return "PurchaseResponseDTO{" +
                "id=" + id +
                ", metalName='" + metalName + '\'' +
                ", agencyName='" + agencyName + '\'' +
                ", requestedQtyTons=" + requestedQtyTons +
                ", pricePerTon=" + pricePerTon +
                ", totalEstimatedValue=" + totalEstimatedValue +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }

    public static PurchaseResponseDTOBuilder builder() {
        return new PurchaseResponseDTOBuilder();
    }

    public static class PurchaseResponseDTOBuilder {
        private UUID id;
        private String metalName;
        private String agencyName;
        private Double requestedQtyTons;
        private BigDecimal pricePerTon;
        private BigDecimal totalEstimatedValue;
        private PurchaseStatus status;
        private LocalDateTime createdAt;

        PurchaseResponseDTOBuilder() {
        }

        public PurchaseResponseDTOBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public PurchaseResponseDTOBuilder metalName(String metalName) {
            this.metalName = metalName;
            return this;
        }

        public PurchaseResponseDTOBuilder agencyName(String agencyName) {
            this.agencyName = agencyName;
            return this;
        }

        public PurchaseResponseDTOBuilder requestedQtyTons(Double requestedQtyTons) {
            this.requestedQtyTons = requestedQtyTons;
            return this;
        }

        public PurchaseResponseDTOBuilder pricePerTon(BigDecimal pricePerTon) {
            this.pricePerTon = pricePerTon;
            return this;
        }

        public PurchaseResponseDTOBuilder totalEstimatedValue(BigDecimal totalEstimatedValue) {
            this.totalEstimatedValue = totalEstimatedValue;
            return this;
        }

        public PurchaseResponseDTOBuilder status(PurchaseStatus status) {
            this.status = status;
            return this;
        }

        public PurchaseResponseDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PurchaseResponseDTO build() {
            return new PurchaseResponseDTO(id, metalName, agencyName, requestedQtyTons, pricePerTon, totalEstimatedValue, status, createdAt);
        }
    }
}
