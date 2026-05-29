package com.nmts.agency.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "metal_listing")
public class MetalListing {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID agencyId;

    @Column(nullable = false)
    private String metalName;

    @Enumerated(EnumType.STRING)
    private MetalCategory metalCategory;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal pricePerTon;

    @Column(nullable = false)
    private Double availableQtyTons;

    private String unit = "ton";

    private Boolean isActive = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public MetalListing() {
    }

    public MetalListing(UUID id, UUID agencyId, String metalName, MetalCategory metalCategory, String description, BigDecimal pricePerTon, Double availableQtyTons, String unit, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.agencyId = agencyId;
        this.metalName = metalName;
        this.metalCategory = metalCategory;
        this.description = description;
        this.pricePerTon = pricePerTon;
        this.availableQtyTons = availableQtyTons;
        this.unit = unit;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static MetalListingBuilder builder() {
        return new MetalListingBuilder();
    }

    public static class MetalListingBuilder {
        private UUID id;
        private UUID agencyId;
        private String metalName;
        private MetalCategory metalCategory;
        private String description;
        private BigDecimal pricePerTon;
        private Double availableQtyTons;
        private String unit = "ton";
        private Boolean isActive = true;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        MetalListingBuilder() {
        }

        public MetalListingBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public MetalListingBuilder agencyId(UUID agencyId) {
            this.agencyId = agencyId;
            return this;
        }

        public MetalListingBuilder metalName(String metalName) {
            this.metalName = metalName;
            return this;
        }

        public MetalListingBuilder metalCategory(MetalCategory metalCategory) {
            this.metalCategory = metalCategory;
            return this;
        }

        public MetalListingBuilder description(String description) {
            this.description = description;
            return this;
        }

        public MetalListingBuilder pricePerTon(BigDecimal pricePerTon) {
            this.pricePerTon = pricePerTon;
            return this;
        }

        public MetalListingBuilder availableQtyTons(Double availableQtyTons) {
            this.availableQtyTons = availableQtyTons;
            return this;
        }

        public MetalListingBuilder unit(String unit) {
            this.unit = unit;
            return this;
        }

        public MetalListingBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public MetalListingBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public MetalListingBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public MetalListing build() {
            return new MetalListing(id, agencyId, metalName, metalCategory, description, pricePerTon, availableQtyTons, unit, isActive, createdAt, updatedAt);
        }

        @Override
        public String toString() {
            return "MetalListingBuilder(id=" + this.id + ", agencyId=" + this.agencyId + ", metalName=" + this.metalName + ", metalCategory=" + this.metalCategory + ", description=" + this.description + ", pricePerTon=" + this.pricePerTon + ", availableQtyTons=" + this.availableQtyTons + ", unit=" + this.unit + ", isActive=" + this.isActive + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
