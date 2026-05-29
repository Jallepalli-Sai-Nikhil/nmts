package com.nmts.agency.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class PurchaseRequestDTO {
    @NotNull(message = "Listing ID is required")
    private UUID listingId;

    @NotNull(message = "Requested quantity is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Quantity must be greater than 0")
    private Double requestedQtyTons;

    private String message;

    public PurchaseRequestDTO() {
    }

    public PurchaseRequestDTO(UUID listingId, Double requestedQtyTons, String message) {
        this.listingId = listingId;
        this.requestedQtyTons = requestedQtyTons;
        this.message = message;
    }

    public UUID getListingId() {
        return listingId;
    }

    public void setListingId(UUID listingId) {
        this.listingId = listingId;
    }

    public Double getRequestedQtyTons() {
        return requestedQtyTons;
    }

    public void setRequestedQtyTons(Double requestedQtyTons) {
        this.requestedQtyTons = requestedQtyTons;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseRequestDTO that = (PurchaseRequestDTO) o;
        return Objects.equals(listingId, that.listingId) &&
                Objects.equals(requestedQtyTons, that.requestedQtyTons) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listingId, requestedQtyTons, message);
    }

    @Override
    public String toString() {
        return "PurchaseRequestDTO{" +
                "listingId=" + listingId +
                ", requestedQtyTons=" + requestedQtyTons +
                ", message='" + message + '\'' +
                '}';
    }
}
