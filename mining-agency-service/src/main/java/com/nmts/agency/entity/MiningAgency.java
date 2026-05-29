package com.nmts.agency.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mining_agency")
public class MiningAgency {

    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private UUID ownerId;

    @Column(unique = true, nullable = false)
    private String agencyName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgencyType agencyType;

    @Column(unique = true, nullable = false)
    private String registrationNumber;

    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private OperationStatus operationStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public MiningAgency() {
    }

    public MiningAgency(UUID id, UUID ownerId, String agencyName, AgencyType agencyType, String registrationNumber, String location, String description, OperationStatus operationStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.agencyName = agencyName;
        this.agencyType = agencyType;
        this.registrationNumber = registrationNumber;
        this.location = location;
        this.description = description;
        this.operationStatus = operationStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public AgencyType getAgencyType() {
        return agencyType;
    }

    public void setAgencyType(AgencyType agencyType) {
        this.agencyType = agencyType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static MiningAgencyBuilder builder() {
        return new MiningAgencyBuilder();
    }

    public static class MiningAgencyBuilder {
        private UUID id;
        private UUID ownerId;
        private String agencyName;
        private AgencyType agencyType;
        private String registrationNumber;
        private String location;
        private String description;
        private OperationStatus operationStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        MiningAgencyBuilder() {
        }

        public MiningAgencyBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public MiningAgencyBuilder ownerId(UUID ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public MiningAgencyBuilder agencyName(String agencyName) {
            this.agencyName = agencyName;
            return this;
        }

        public MiningAgencyBuilder agencyType(AgencyType agencyType) {
            this.agencyType = agencyType;
            return this;
        }

        public MiningAgencyBuilder registrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public MiningAgencyBuilder location(String location) {
            this.location = location;
            return this;
        }

        public MiningAgencyBuilder description(String description) {
            this.description = description;
            return this;
        }

        public MiningAgencyBuilder operationStatus(OperationStatus operationStatus) {
            this.operationStatus = operationStatus;
            return this;
        }

        public MiningAgencyBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public MiningAgencyBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public MiningAgency build() {
            return new MiningAgency(id, ownerId, agencyName, agencyType, registrationNumber, location, description, operationStatus, createdAt, updatedAt);
        }

        @Override
        public String toString() {
            return "MiningAgencyBuilder(id=" + this.id + ", ownerId=" + this.ownerId + ", agencyName=" + this.agencyName + ", agencyType=" + this.agencyType + ", registrationNumber=" + this.registrationNumber + ", location=" + this.location + ", description=" + this.description + ", operationStatus=" + this.operationStatus + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
