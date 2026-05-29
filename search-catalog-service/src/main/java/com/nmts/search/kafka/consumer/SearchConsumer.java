package com.nmts.search.kafka.consumer;

import com.nmts.search.entity.MetalCatalog;
import com.nmts.search.repository.MetalCatalogRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Service
public class SearchConsumer {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SearchConsumer.class);

    private final MetalCatalogRepository catalogRepository;

    public SearchConsumer(MetalCatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public static class ListingCreatedEvent {
        private UUID listingId;
        private UUID agencyId;
        private String agencyName;
        private String metalName;
        private BigDecimal pricePerTon;
        private Double availableQtyTons;

        public ListingCreatedEvent() {
        }

        public ListingCreatedEvent(UUID listingId, UUID agencyId, String agencyName, String metalName, BigDecimal pricePerTon, Double availableQtyTons) {
            this.listingId = listingId;
            this.agencyId = agencyId;
            this.agencyName = agencyName;
            this.metalName = metalName;
            this.pricePerTon = pricePerTon;
            this.availableQtyTons = availableQtyTons;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListingCreatedEvent that = (ListingCreatedEvent) o;
            return Objects.equals(listingId, that.listingId) && Objects.equals(agencyId, that.agencyId) && Objects.equals(agencyName, that.agencyName) && Objects.equals(metalName, that.metalName) && Objects.equals(pricePerTon, that.pricePerTon) && Objects.equals(availableQtyTons, that.availableQtyTons);
        }

        @Override
        public int hashCode() {
            return Objects.hash(listingId, agencyId, agencyName, metalName, pricePerTon, availableQtyTons);
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
                    '}';
        }
    }

    @KafkaListener(topics = "listing.created", groupId = "nmts-group")
    @CacheEvict(value = "metals", allEntries = true)
    public void consumeListingCreated(ListingCreatedEvent event) {
        log.info("Updating search catalog with new listing: {}", event.getMetalName());
        MetalCatalog item = MetalCatalog.builder()
                .id(event.getListingId())
                .agencyId(event.getAgencyId())
                .agencyName(event.getAgencyName())
                .metalName(event.getMetalName())
                .pricePerTon(event.getPricePerTon())
                .availableQtyTons(event.getAvailableQtyTons())
                .build();
        catalogRepository.save(item);
    }

    @KafkaListener(topics = "license.revoked", groupId = "search-service-group")
    @CacheEvict(value = "metals", allEntries = true)
    public void consumeLicenseRevoked(String agencyId) {
        log.info("License revoked for agency {}, evicting catalog cache", agencyId);
    }
}
