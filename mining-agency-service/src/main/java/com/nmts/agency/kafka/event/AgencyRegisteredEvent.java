package com.nmts.agency.kafka.event;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class AgencyRegisteredEvent {
    private UUID agencyId;
    private UUID ownerId;
    private String agencyName;
    private String registrationNumber;
    private LocalDateTime timestamp;

    public AgencyRegisteredEvent() {
    }

    public AgencyRegisteredEvent(UUID agencyId, UUID ownerId, String agencyName, String registrationNumber, LocalDateTime timestamp) {
        this.agencyId = agencyId;
        this.ownerId = ownerId;
        this.agencyName = agencyName;
        this.registrationNumber = registrationNumber;
        this.timestamp = timestamp;
    }

    public UUID getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(UUID agencyId) {
        this.agencyId = agencyId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
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
        AgencyRegisteredEvent that = (AgencyRegisteredEvent) o;
        return Objects.equals(agencyId, that.agencyId) &&
                Objects.equals(ownerId, that.ownerId) &&
                Objects.equals(agencyName, that.agencyName) &&
                Objects.equals(registrationNumber, that.registrationNumber) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agencyId, ownerId, agencyName, registrationNumber, timestamp);
    }

    @Override
    public String toString() {
        return "AgencyRegisteredEvent{" +
                "agencyId=" + agencyId +
                ", ownerId=" + ownerId +
                ", agencyName='" + agencyName + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public static AgencyRegisteredEventBuilder builder() {
        return new AgencyRegisteredEventBuilder();
    }

    public static class AgencyRegisteredEventBuilder {
        private UUID agencyId;
        private UUID ownerId;
        private String agencyName;
        private String registrationNumber;
        private LocalDateTime timestamp;

        AgencyRegisteredEventBuilder() {
        }

        public AgencyRegisteredEventBuilder agencyId(UUID agencyId) {
            this.agencyId = agencyId;
            return this;
        }

        public AgencyRegisteredEventBuilder ownerId(UUID ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public AgencyRegisteredEventBuilder agencyName(String agencyName) {
            this.agencyName = agencyName;
            return this;
        }

        public AgencyRegisteredEventBuilder registrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public AgencyRegisteredEventBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public AgencyRegisteredEvent build() {
            return new AgencyRegisteredEvent(agencyId, ownerId, agencyName, registrationNumber, timestamp);
        }
    }
}
