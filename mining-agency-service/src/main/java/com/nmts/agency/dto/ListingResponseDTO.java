package com.nmts.agency.dto;

import com.nmts.agency.entity.MetalCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ListingResponseDTO {
    private UUID id;
    private UUID agencyId;
    private String agencyName;
    private String metalName;
    private MetalCategory metalCategory;
    private String description;
    private BigDecimal pricePerTon;
    private Double availableQtyTons;
    private String unit;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public ListingResponseDTO() {
    }

    public ListingResponseDTO(UUID id, UUID agencyId, String agencyName, String metalName, MetalCategory metalCategory, String description, BigDecimal pricePerTon, Double availableQtyTons, String unit, Boolean isActive, LocalDateTime createdAt) {
        this.id = id;
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.metalName = metalName;
        this.metalCategory = metalCategory;
        this.description = description;
        this.pricePerTon = pricePerTon;
        this.availableQtyTons = availableQtyTons;
        this.unit = unit;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public MetalCategory getMetalCategory() {
        return metalCategory;
    }

    public void setMetalCategory(MetalCategory metalCategory) {
        this.metalCategory = metalCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPricePerTon() {
        return pricePerTon;
    }

    public void setPricePerTon(BigDecimal pricePerTon) {
        this.pricePerTon = pricePerTon;
    }

    public Double getAvailableQtyTons() {
        return availableQtyTons;
    }

    public void setAvailableQtyTons(Double availableQtyTons) {
        this.availableQtyTons = availableQtyTons;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
        ListingResponseDTO that = (ListingResponseDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(agencyId, that.agencyId) &&
                Objects.equals(agencyName, that.agencyName) &&
                Objects.equals(metalName, that.metalName) &&
                metalCategory == that.metalCategory &&
                Objects.equals(description, that.description) &&
                Objects.equals(pricePerTon, that.pricePerTon) &&
                Objects.equals(availableQtyTons, that.availableQtyTons) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, agencyId, agencyName, metalName, metalCategory, description, pricePerTon, availableQtyTons, unit, isActive, createdAt);
    }

    @Override
    public String toString() {
        return "ListingResponseDTO{" +
                "id=" + id +
                ", agencyId=" + agencyId +
                ", agencyName='" + agencyName + '\'' +
                ", metalName='" + metalName + '\'' +
                ", metalCategory=" + metalCategory +
                ", description='" + description + '\'' +
                ", pricePerTon=" + pricePerTon +
                ", availableQtyTons=" + availableQtyTons +
                ", unit='" + unit + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }

    public static ListingResponseDTOBuilder builder() {
        return new ListingResponseDTOBuilder();
    }

    public static class ListingResponseDTOBuilder {
        private UUID id;
        private UUID agencyId;
        private String agencyName;
        private String metalName;
        private MetalCategory metalCategory;
        private String description;
        private BigDecimal pricePerTon;
        private Double availableQtyTons;
        private String unit;
        private Boolean isActive;
        private LocalDateTime createdAt;

        ListingResponseDTOBuilder() {
        }

        public ListingResponseDTOBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ListingResponseDTOBuilder agencyId(UUID agencyId) {
            this.agencyId = agencyId;
            return this;
        }

        public ListingResponseDTOBuilder agencyName(String agencyName) {
            this.agencyName = agencyName;
            return this;
        }

        public ListingResponseDTOBuilder metalName(String metalName) {
            this.metalName = metalName;
            return this;
        }

        public ListingResponseDTOBuilder metalCategory(MetalCategory metalCategory) {
            this.metalCategory = metalCategory;
            return this;
        }

        public ListingResponseDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ListingResponseDTOBuilder pricePerTon(BigDecimal pricePerTon) {
            this.pricePerTon = pricePerTon;
            return this;
        }

        public ListingResponseDTOBuilder availableQtyTons(Double availableQtyTons) {
            this.availableQtyTons = availableQtyTons;
            return this;
        }

        public ListingResponseDTOBuilder unit(String unit) {
            this.unit = unit;
            return this;
        }

        public ListingResponseDTOBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public ListingResponseDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ListingResponseDTO build() {
            return new ListingResponseDTO(id, agencyId, agencyName, metalName, metalCategory, description, pricePerTon, availableQtyTons, unit, isActive, createdAt);
        }
    }
}
