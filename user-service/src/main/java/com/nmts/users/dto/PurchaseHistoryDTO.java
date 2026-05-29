package com.nmts.users.dto;

import com.nmts.users.entity.PurchaseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class PurchaseHistoryDTO {
    private UUID id;
    private String agencyName;
    private String metalName;
    private Double requestedQtyTons;
    private BigDecimal pricePerTon;
    private BigDecimal totalValue;
    private PurchaseStatus status;
    private LocalDateTime processedAt;

    public PurchaseHistoryDTO() {}

    public PurchaseHistoryDTO(UUID id, String agencyName, String metalName, Double requestedQtyTons, BigDecimal pricePerTon, BigDecimal totalValue, PurchaseStatus status, LocalDateTime processedAt) {
        this.id = id;
        this.agencyName = agencyName;
        this.metalName = metalName;
        this.requestedQtyTons = requestedQtyTons;
        this.pricePerTon = pricePerTon;
        this.totalValue = totalValue;
        this.status = status;
        this.processedAt = processedAt;
    }

    public static PurchaseHistoryDTOBuilder builder() {
        return new PurchaseHistoryDTOBuilder();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getAgencyName() { return agencyName; }
    public void setAgencyName(String agencyName) { this.agencyName = agencyName; }

    public String getMetalName() { return metalName; }
    public void setMetalName(String metalName) { this.metalName = metalName; }

    public Double getRequestedQtyTons() { return requestedQtyTons; }
    public void setRequestedQtyTons(Double requestedQtyTons) { this.requestedQtyTons = requestedQtyTons; }

    public BigDecimal getPricePerTon() { return pricePerTon; }
    public void setPricePerTon(BigDecimal pricePerTon) { this.pricePerTon = pricePerTon; }

    public BigDecimal getTotalValue() { return totalValue; }
    public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }

    public PurchaseStatus getStatus() { return status; }
    public void setStatus(PurchaseStatus status) { this.status = status; }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseHistoryDTO that = (PurchaseHistoryDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(agencyName, that.agencyName) && Objects.equals(metalName, that.metalName) && Objects.equals(requestedQtyTons, that.requestedQtyTons) && Objects.equals(pricePerTon, that.pricePerTon) && Objects.equals(totalValue, that.totalValue) && status == that.status && Objects.equals(processedAt, that.processedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, agencyName, metalName, requestedQtyTons, pricePerTon, totalValue, status, processedAt);
    }

    @Override
    public String toString() {
        return "PurchaseHistoryDTO{" +
                "id=" + id +
                ", agencyName='" + agencyName + '\'' +
                ", metalName='" + metalName + '\'' +
                ", requestedQtyTons=" + requestedQtyTons +
                ", pricePerTon=" + pricePerTon +
                ", totalValue=" + totalValue +
                ", status=" + status +
                ", processedAt=" + processedAt +
                '}';
    }

    public static class PurchaseHistoryDTOBuilder {
        private UUID id;
        private String agencyName;
        private String metalName;
        private Double requestedQtyTons;
        private BigDecimal pricePerTon;
        private BigDecimal totalValue;
        private PurchaseStatus status;
        private LocalDateTime processedAt;

        PurchaseHistoryDTOBuilder() {}

        public PurchaseHistoryDTOBuilder id(UUID id) { this.id = id; return this; }
        public PurchaseHistoryDTOBuilder agencyName(String agencyName) { this.agencyName = agencyName; return this; }
        public PurchaseHistoryDTOBuilder metalName(String metalName) { this.metalName = metalName; return this; }
        public PurchaseHistoryDTOBuilder requestedQtyTons(Double requestedQtyTons) { this.requestedQtyTons = requestedQtyTons; return this; }
        public PurchaseHistoryDTOBuilder pricePerTon(BigDecimal pricePerTon) { this.pricePerTon = pricePerTon; return this; }
        public PurchaseHistoryDTOBuilder totalValue(BigDecimal totalValue) { this.totalValue = totalValue; return this; }
        public PurchaseHistoryDTOBuilder status(PurchaseStatus status) { this.status = status; return this; }
        public PurchaseHistoryDTOBuilder processedAt(LocalDateTime processedAt) { this.processedAt = processedAt; return this; }

        public PurchaseHistoryDTO build() {
            return new PurchaseHistoryDTO(id, agencyName, metalName, requestedQtyTons, pricePerTon, totalValue, status, processedAt);
        }
    }
}
