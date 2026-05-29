package com.nmts.license.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "licenses")
public class License {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID agencyId;

    @Column(nullable = false)
    private String agencyName;

    @Enumerated(EnumType.STRING)
    private LicenseType licenseType;

    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private boolean isActive;

    public License() {
    }

    public License(UUID id, UUID agencyId, String agencyName, LicenseType licenseType, LocalDateTime issuedAt, LocalDateTime expiresAt, boolean isActive) {
        this.id = id;
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.licenseType = licenseType;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.isActive = isActive;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public static LicenseBuilder builder() {
        return new LicenseBuilder();
    }

    public static class LicenseBuilder {
        private UUID id;
        private UUID agencyId;
        private String agencyName;
        private LicenseType licenseType;
        private LocalDateTime issuedAt;
        private LocalDateTime expiresAt;
        private boolean isActive;

        public LicenseBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public LicenseBuilder agencyId(UUID agencyId) {
            this.agencyId = agencyId;
            return this;
        }

        public LicenseBuilder agencyName(String agencyName) {
            this.agencyName = agencyName;
            return this;
        }

        public LicenseBuilder licenseType(LicenseType licenseType) {
            this.licenseType = licenseType;
            return this;
        }

        public LicenseBuilder issuedAt(LocalDateTime issuedAt) {
            this.issuedAt = issuedAt;
            return this;
        }

        public LicenseBuilder expiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public LicenseBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public License build() {
            return new License(id, agencyId, agencyName, licenseType, issuedAt, expiresAt, isActive);
        }
    }
}
