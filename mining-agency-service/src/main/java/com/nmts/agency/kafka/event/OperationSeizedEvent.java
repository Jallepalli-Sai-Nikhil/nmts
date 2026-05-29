package com.nmts.agency.kafka.event;

import java.time.LocalDateTime;
import java.util.UUID;

public class OperationSeizedEvent {
    private UUID seizureOrderId;
    private UUID agencyId;
    private UUID officerId;
    private String reason;
    private LocalDateTime issuedAt;

    public OperationSeizedEvent() {
    }

    public OperationSeizedEvent(UUID seizureOrderId, UUID agencyId, UUID officerId, String reason, LocalDateTime issuedAt) {
        this.seizureOrderId = seizureOrderId;
        this.agencyId = agencyId;
        this.officerId = officerId;
        this.reason = reason;
        this.issuedAt = issuedAt;
    }

    public UUID getSeizureOrderId() {
        return seizureOrderId;
    }

    public void setSeizureOrderId(UUID seizureOrderId) {
        this.seizureOrderId = seizureOrderId;
    }

    public UUID getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(UUID agencyId) {
        this.agencyId = agencyId;
    }

    public UUID getOfficerId() {
        return officerId;
    }

    public void setOfficerId(UUID officerId) {
        this.officerId = officerId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public static OperationSeizedEventBuilder builder() {
        return new OperationSeizedEventBuilder();
    }

    public static class OperationSeizedEventBuilder {
        private UUID seizureOrderId;
        private UUID agencyId;
        private UUID officerId;
        private String reason;
        private LocalDateTime issuedAt;

        OperationSeizedEventBuilder() {
        }

        public OperationSeizedEventBuilder seizureOrderId(UUID seizureOrderId) {
            this.seizureOrderId = seizureOrderId;
            return this;
        }

        public OperationSeizedEventBuilder agencyId(UUID agencyId) {
            this.agencyId = agencyId;
            return this;
        }

        public OperationSeizedEventBuilder officerId(UUID officerId) {
            this.officerId = officerId;
            return this;
        }

        public OperationSeizedEventBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public OperationSeizedEventBuilder issuedAt(LocalDateTime issuedAt) {
            this.issuedAt = issuedAt;
            return this;
        }

        public OperationSeizedEvent build() {
            return new OperationSeizedEvent(seizureOrderId, agencyId, officerId, reason, issuedAt);
        }

        @Override
        public String toString() {
            return "OperationSeizedEventBuilder(seizureOrderId=" + this.seizureOrderId + ", agencyId=" + this.agencyId + ", officerId=" + this.officerId + ", reason=" + this.reason + ", issuedAt=" + this.issuedAt + ")";
        }
    }
}
