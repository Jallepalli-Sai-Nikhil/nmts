package com.nmts.agency.dto;

import com.nmts.agency.entity.AgencyType;
import com.nmts.agency.entity.OperationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class AgencyResponseDTO {
    private UUID id;
    private String agencyName;
    private AgencyType agencyType;
    private String registrationNumber;
    private String location;
    private OperationStatus operationStatus;
    private LocalDateTime createdAt;

    public AgencyResponseDTO() {
    }

    public AgencyResponseDTO(UUID id, String agencyName, AgencyType agencyType, String registrationNumber, String location, OperationStatus operationStatus, LocalDateTime createdAt) {
        this.id = id;
        this.agencyName = agencyName;
        this.agencyType = agencyType;
        this.registrationNumber = registrationNumber;
        this.location = location;
        this.operationStatus = operationStatus;
        this.createdAt = createdAt;
    }

    public static AgencyResponseDTOBuilder builder() {
        return new AgencyResponseDTOBuilder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public static class AgencyResponseDTOBuilder {
        private UUID id;
        private String agencyName;
        private AgencyType agencyType;
        private String registrationNumber;
        private String location;
        private OperationStatus operationStatus;
        private LocalDateTime createdAt;

        AgencyResponseDTOBuilder() {
        }

        public AgencyResponseDTOBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public AgencyResponseDTOBuilder agencyName(String agencyName) {
            this.agencyName = agencyName;
            return this;
        }

        public AgencyResponseDTOBuilder agencyType(AgencyType agencyType) {
            this.agencyType = agencyType;
            return this;
        }

        public AgencyResponseDTOBuilder registrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public AgencyResponseDTOBuilder location(String location) {
            this.location = location;
            return this;
        }

        public AgencyResponseDTOBuilder operationStatus(OperationStatus operationStatus) {
            this.operationStatus = operationStatus;
            return this;
        }

        public AgencyResponseDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AgencyResponseDTO build() {
            return new AgencyResponseDTO(id, agencyName, agencyType, registrationNumber, location, operationStatus, createdAt);
        }
    }
}
