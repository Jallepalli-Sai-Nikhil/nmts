package com.nmts.search.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "catalog")
public class MetalCatalog {
    @Id
    private UUID id;

    private UUID agencyId;
    private String agencyName;
    private String metalName;
    private BigDecimal pricePerTon;
    private Double availableQtyTons;

    public MetalCatalog() {
    }

    public MetalCatalog(UUID id, UUID agencyId, String agencyName, String metalName, BigDecimal pricePerTon, Double availableQtyTons) {
        this.id = id;
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.metalName = metalName;
        this.pricePerTon = pricePerTon;
        this.availableQtyTons = availableQtyTons;
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

    public static MetalCatalogBuilder builder() {
        return new MetalCatalogBuilder();
    }

    public static class MetalCatalogBuilder {
        private UUID id;
        private UUID agencyId;
        private String agencyName;
        private String metalName;
        private BigDecimal pricePerTon;
        private Double availableQtyTons;

        public MetalCatalogBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public MetalCatalogBuilder agencyId(UUID agencyId) {
            this.agencyId = agencyId;
            return this;
        }

        public MetalCatalogBuilder agencyName(String agencyName) {
            this.agencyName = agencyName;
            return this;
        }

        public MetalCatalogBuilder metalName(String metalName) {
            this.metalName = metalName;
            return this;
        }

        public MetalCatalogBuilder pricePerTon(BigDecimal pricePerTon) {
            this.pricePerTon = pricePerTon;
            return this;
        }

        public MetalCatalogBuilder availableQtyTons(Double availableQtyTons) {
            this.availableQtyTons = availableQtyTons;
            return this;
        }

        public MetalCatalog build() {
            return new MetalCatalog(id, agencyId, agencyName, metalName, pricePerTon, availableQtyTons);
        }
    }
}
