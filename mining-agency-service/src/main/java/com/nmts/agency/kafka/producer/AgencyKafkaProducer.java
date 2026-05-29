package com.nmts.agency.kafka.producer;

import com.nmts.agency.kafka.event.ListingCreatedEvent;
import com.nmts.agency.kafka.event.PurchaseApprovedEvent;
import com.nmts.agency.kafka.event.PurchaseRejectedEvent;
import com.nmts.agency.kafka.event.PurchaseRequestedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AgencyKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(AgencyKafkaProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AgencyKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishListingCreated(ListingCreatedEvent event) {
        log.info("Publishing listing.created event for listing: {}", event.getListingId());
        kafkaTemplate.send("listing.created", event.getListingId().toString(), event);
    }

    public void publishAgencyRegistered(com.nmts.agency.kafka.event.AgencyRegisteredEvent event) {
        log.info("Publishing agency.registered event for agency: {}", event.getAgencyId());
        kafkaTemplate.send("agency.registered", event.getAgencyId().toString(), event);
    }

    public void publishPurchaseRequested(PurchaseRequestedEvent event) {
        log.info("Publishing purchase.requested event for request: {}", event.getRequestId());
        kafkaTemplate.send("purchase.requested", event.getRequestId().toString(), event);
    }

    public void publishPurchaseApproved(PurchaseApprovedEvent event) {
        log.info("Publishing purchase.approved event for request: {}", event.getRequestId());
        kafkaTemplate.send("purchase.approved", event.getRequestId().toString(), event);
    }

    public void publishPurchaseRejected(PurchaseRejectedEvent event) {
        log.info("Publishing purchase.rejected event for request: {}", event.getRequestId());
        kafkaTemplate.send("purchase.rejected", event.getRequestId().toString(), event);
    }
}
