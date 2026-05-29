package com.nmts.agency.client;

import com.nmts.agency.dto.LicenseStatusDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "license-service", path = "/licenses")
public interface LicenseServiceClient {
    @GetMapping("/agency/{agencyId}/status")
    LicenseStatusDTO getAgencyLicenseStatus(@PathVariable("agencyId") UUID agencyId);
}
