package com.nmts.agency.dto;

import com.nmts.agency.entity.MetalCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateListingDTO {
    @NotBlank(message = "Metal name is required")
    private String metalName;

    @NotNull(message = "Metal category is required")
    private MetalCategory metalCategory;

    private String description;

    @NotNull(message = "Price per ton is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal pricePerTon;

    @NotNull(message = "Available quantity is required")
    @DecimalMin(value = "0.0", message = "Quantity cannot be negative")
    private Double availableQtyTons;

    public CreateListingDTO() {
    }

    public CreateListingDTO(String metalName, MetalCategory metalCategory, String description, BigDecimal pricePerTon, Double availableQtyTons) {
        this.metalName = metalName;
        this.metalCategory = metalCategory;
        this.description = description;
        this.pricePerTon = pricePerTon;
        this.availableQtyTons = availableQtyTons;
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
}
