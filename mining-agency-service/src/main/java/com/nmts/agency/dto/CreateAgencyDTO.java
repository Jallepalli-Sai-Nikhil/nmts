package com.nmts.agency.dto;

import com.nmts.agency.entity.AgencyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateAgencyDTO {
    @NotBlank(message = "Agency name is required")
    private String agencyName;

    @NotNull(message = "Agency type is required")
    private AgencyType agencyType;

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    private String location;
    private String description;

    public CreateAgencyDTO() {
    }

    public CreateAgencyDTO(String agencyName, AgencyType agencyType, String registrationNumber, String location, String description) {
        this.agencyName = agencyName;
        this.agencyType = agencyType;
        this.registrationNumber = registrationNumber;
        this.location = location;
        this.description = description;
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
}
