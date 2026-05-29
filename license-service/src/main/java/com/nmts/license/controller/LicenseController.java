package com.nmts.license.controller;

import com.nmts.license.dto.GrantLicenseRequest;
import com.nmts.license.dto.LicenseStatusDTO;
import com.nmts.license.entity.License;
import com.nmts.license.service.LicenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/licenses")
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @PostMapping("/grant")
    public ResponseEntity<License> grantLicense(@RequestBody GrantLicenseRequest request) {
        return ResponseEntity.ok(licenseService.grantLicense(request));
    }

    @PostMapping("/{licenseId}/revoke")
    public ResponseEntity<Void> revokeLicense(@PathVariable UUID licenseId) {
        licenseService.revokeLicense(licenseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/agency/{agencyId}/status")
    public ResponseEntity<LicenseStatusDTO> getStatus(@PathVariable UUID agencyId) {
        return ResponseEntity.ok(licenseService.getStatus(agencyId));
    }
}
