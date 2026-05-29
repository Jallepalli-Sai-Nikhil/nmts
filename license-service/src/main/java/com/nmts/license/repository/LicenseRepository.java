package com.nmts.license.repository;

import com.nmts.license.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface LicenseRepository extends JpaRepository<License, UUID> {
    Optional<License> findByAgencyIdAndIsActiveTrue(UUID agencyId);
}
