package com.nmts.license.service;

import com.nmts.license.dto.GrantLicenseRequest;
import com.nmts.license.dto.LicenseStatusDTO;
import com.nmts.license.entity.License;
import com.nmts.license.repository.LicenseRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final com.nmts.license.kafka.producer.LicenseProducer licenseProducer;

    public LicenseService(LicenseRepository licenseRepository, com.nmts.license.kafka.producer.LicenseProducer licenseProducer) {
        this.licenseRepository = licenseRepository;
        this.licenseProducer = licenseProducer;
    }

    public License grantLicense(GrantLicenseRequest request) {
        License license = License.builder()
                .id(UUID.randomUUID())
                .agencyId(request.getAgencyId())
                .agencyName(request.getAgencyName())
                .licenseType(request.getLicenseType())
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(request.getDurationDays()))
                .isActive(true)
                .build();

        License saved = licenseRepository.save(license);
        
        licenseProducer.publishLicenseGranted(com.nmts.license.kafka.producer.LicenseProducer.LicenseGrantedEvent.builder()
                .licenseId(saved.getId())
                .agencyId(saved.getAgencyId())
                .agencyName(saved.getAgencyName())
                .licenseType(saved.getLicenseType().name())
                .expiresAt(saved.getExpiresAt())
                .timestamp(LocalDateTime.now())
                .build());

        return saved;
    }

    public void revokeLicense(UUID licenseId) {
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(() -> new RuntimeException("License not found"));
        license.setActive(false);
        licenseRepository.save(license);
        
        licenseProducer.publishLicenseRevoked(license.getAgencyId());
    }

    public LicenseStatusDTO getStatus(UUID agencyId) {
        return licenseRepository.findByAgencyIdAndIsActiveTrue(agencyId)
                .map(l -> LicenseStatusDTO.builder()
                        .hasActiveLicense(l.isActive() && l.getExpiresAt().isAfter(LocalDateTime.now()))
                        .licenseId(l.getId())
                        .expiresAt(l.getExpiresAt())
                        .build())
                .orElse(LicenseStatusDTO.builder().hasActiveLicense(false).build());
    }
}
