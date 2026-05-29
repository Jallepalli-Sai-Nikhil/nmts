package com.nmts.agency.kafka.event;

import java.time.LocalDateTime;
import java.util.UUID;

public class PurchaseRejectedEvent {
    private UUID requestId;
    private UUID customerId;
    private UUID agencyId;
    private String metalName;
    private String rejectionReason;
    private LocalDateTime rejectedAt;

    public PurchaseRejectedEvent() {
    }

    public PurchaseRejectedEvent(UUID requestId, UUID customerId, UUID agencyId, String metalName, String rejectionReason, LocalDateTime rejectedAt) {
        this.requestId = requestId;
        this.customerId = customerId;
        this.agencyId = agencyId;
        this.metalName = metalName;
        this.rejectionReason = rejectionReason;
        this.rejectedAt = rejectedAt;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
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

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public static PurchaseRejectedEventBuilder builder() {
        return new PurchaseRejectedEventBuilder();
    }

    public static class PurchaseRejectedEventBuilder {
        private UUID requestId;
        private UUID customerId;
        private UUID agencyId;
        private String metalName;
        private String rejectionReason;
        private LocalDateTime rejectedAt;

        PurchaseRejectedEventBuilder() {
        }

        public PurchaseRejectedEventBuilder requestId(UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public PurchaseRejectedEventBuilder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public PurchaseRejectedEventBuilder agencyId(UUID agencyId) {
            this.agencyId = agencyId;
            return this;
        }

        public PurchaseRejectedEventBuilder metalName(String metalName) {
            this.metalName = metalName;
            return this;
        }

        public PurchaseRejectedEventBuilder rejectionReason(String rejectionReason) {
            this.rejectionReason = rejectionReason;
            return this;
        }

        public PurchaseRejectedEventBuilder rejectedAt(LocalDateTime rejectedAt) {
            this.rejectedAt = rejectedAt;
            return this;
        }

        public PurchaseRejectedEvent build() {
            return new PurchaseRejectedEvent(requestId, customerId, agencyId, metalName, rejectionReason, rejectedAt);
        }

        @Override
        public String toString() {
            return "PurchaseRejectedEventBuilder(requestId=" + this.requestId + ", customerId=" + this.customerId + ", agencyId=" + this.agencyId + ", metalName=" + this.metalName + ", rejectionReason=" + this.rejectionReason + ", rejectedAt=" + this.rejectedAt + ")";
        }
    }
}
