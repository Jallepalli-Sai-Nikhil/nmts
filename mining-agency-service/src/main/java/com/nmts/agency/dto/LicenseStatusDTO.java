package com.nmts.agency.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class LicenseStatusDTO {
    private boolean hasActiveLicense;
    private UUID licenseId;
    private LocalDateTime expiresAt;

    public LicenseStatusDTO() {
    }

    public LicenseStatusDTO(boolean hasActiveLicense, UUID licenseId, LocalDateTime expiresAt) {
        this.hasActiveLicense = hasActiveLicense;
        this.licenseId = licenseId;
        this.expiresAt = expiresAt;
    }

    public boolean isHasActiveLicense() {
        return hasActiveLicense;
    }

    public void setHasActiveLicense(boolean hasActiveLicense) {
        this.hasActiveLicense = hasActiveLicense;
    }

    public UUID getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(UUID licenseId) {
        this.licenseId = licenseId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LicenseStatusDTO that = (LicenseStatusDTO) o;
        return hasActiveLicense == that.hasActiveLicense &&
                Objects.equals(licenseId, that.licenseId) &&
                Objects.equals(expiresAt, that.expiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasActiveLicense, licenseId, expiresAt);
    }

    @Override
    public String toString() {
        return "LicenseStatusDTO{" +
                "hasActiveLicense=" + hasActiveLicense +
                ", licenseId=" + licenseId +
                ", expiresAt=" + expiresAt +
                '}';
    }
}
