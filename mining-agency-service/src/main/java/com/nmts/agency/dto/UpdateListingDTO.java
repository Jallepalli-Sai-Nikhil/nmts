package com.nmts.agency.dto;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Objects;

public class UpdateListingDTO {
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal pricePerTon;

    @DecimalMin(value = "0.0", message = "Quantity cannot be negative")
    private Double availableQtyTons;

    private Boolean isActive;

    public UpdateListingDTO() {
    }

    public UpdateListingDTO(String description, BigDecimal pricePerTon, Double availableQtyTons, Boolean isActive) {
        this.description = description;
        this.pricePerTon = pricePerTon;
        this.availableQtyTons = availableQtyTons;
        this.isActive = isActive;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateListingDTO that = (UpdateListingDTO) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(pricePerTon, that.pricePerTon) &&
                Objects.equals(availableQtyTons, that.availableQtyTons) &&
                Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, pricePerTon, availableQtyTons, isActive);
    }

    @Override
    public String toString() {
        return "UpdateListingDTO{" +
                "description='" + description + '\'' +
                ", pricePerTon=" + pricePerTon +
                ", availableQtyTons=" + availableQtyTons +
                ", isActive=" + isActive +
                '}';
    }
}
