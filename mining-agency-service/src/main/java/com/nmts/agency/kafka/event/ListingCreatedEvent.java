package com.nmts.agency.kafka.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ListingCreatedEvent {
    private UUID listingId;
    private UUID agencyId;
    private String agencyName;
    private String metalName;
    private BigDecimal pricePerTon;
    private Double availableQtyTons;
    private LocalDateTime timestamp;

    public ListingCreatedEvent() {
    }

    public ListingCreatedEvent(UUID listingId, UUID agencyId, String agencyName, String metalName, BigDecimal pricePerTon, Double availableQtyTons, LocalDateTime timestamp) {
        this.listingId = listingId;
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.metalName = metalName;
        this.pricePerTon = pricePerTon;
        this.availableQtyTons = availableQtyTons;
        this.timestamp = timestamp;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingCreatedEvent that = (ListingCreatedEvent) o;
        return Objects.equals(listingId, that.listingId) &&
                Objects.equals(agencyId, that.agencyId) &&
                Objects.equals(agencyName, that.agencyName) &&
                Objects.equals(metalName, that.metalName) &&
                Objects.equals(pricePerTon, that.pricePerTon) &&
                Objects.equals(availableQtyTons, that.availableQtyTons) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listingId, agencyId, agencyName, metalName, pricePerTon, availableQtyTons, timestamp);
    }

    @Override
    public String toString() {
        return "ListingCreatedEvent{" +
                "listingId=" + listingId +
                ", agencyId=" + agencyId +
                ", agencyName='" + agencyName + '\'' +
                ", metalName='" + metalName + '\'' +
                ", pricePerTon=" + pricePerTon +
                ", availableQtyTons=" + availableQtyTons +
                ", timestamp=" + timestamp +
                '}';
    }

    public static ListingCreatedEventBuilder builder() {
        return new ListingCreatedEventBuilder();
    }

    public static class ListingCreatedEventBuilder {
        private UUID listingId;
        private UUID agencyId;
        private String agencyName;
        private String metalName;
        private BigDecimal pricePerTon;
        private Double availableQtyTons;
        private LocalDateTime timestamp;

        ListingCreatedEventBuilder() {
        }

        public ListingCreatedEventBuilder listingId(UUID listingId) {
            this.listingId = listingId;
            return this;
        }

        public ListingCreatedEventBuilder agencyId(UUID agencyId) {
            this.agencyId = agencyId;
            return this;
        }

        public ListingCreatedEventBuilder agencyName(String agencyName) {
            this.agencyName = agencyName;
            return this;
        }

        public ListingCreatedEventBuilder metalName(String metalName) {
            this.metalName = metalName;
            return this;
        }

        public ListingCreatedEventBuilder pricePerTon(BigDecimal pricePerTon) {
            this.pricePerTon = pricePerTon;
            return this;
        }

        public ListingCreatedEventBuilder availableQtyTons(Double availableQtyTons) {
            this.availableQtyTons = availableQtyTons;
            return this;
        }

        public ListingCreatedEventBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ListingCreatedEvent build() {
            return new ListingCreatedEvent(listingId, agencyId, agencyName, metalName, pricePerTon, availableQtyTons, timestamp);
        }
    }
}
