package com.nmts.license.dto;

import com.nmts.license.entity.LicenseType;
import java.util.Objects;
import java.util.UUID;

public class GrantLicenseRequest {
    private UUID agencyId;
    private String agencyName;
    private LicenseType licenseType;
    private int durationDays;

    public GrantLicenseRequest() {
    }

    public GrantLicenseRequest(UUID agencyId, String agencyName, LicenseType licenseType, int durationDays) {
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.licenseType = licenseType;
        this.durationDays = durationDays;
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

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrantLicenseRequest that = (GrantLicenseRequest) o;
        return durationDays == that.durationDays && Objects.equals(agencyId, that.agencyId) && Objects.equals(agencyName, that.agencyName) && licenseType == that.licenseType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(agencyId, agencyName, licenseType, durationDays);
    }

    @Override
    public String toString() {
        return "GrantLicenseRequest{" +
                "agencyId=" + agencyId +
                ", agencyName='" + agencyName + '\'' +
                ", licenseType=" + licenseType +
                ", durationDays=" + durationDays +
                '}';
    }
}
