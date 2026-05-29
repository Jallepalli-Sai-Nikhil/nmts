package com.nmts.agency.controller;

import com.nmts.agency.dto.AgencyResponseDTO;
import com.nmts.agency.dto.ApiResponse;
import com.nmts.agency.dto.CreateAgencyDTO;
import com.nmts.agency.dto.UpdateAgencyStatusDTO;
import com.nmts.agency.entity.OperationStatus;
import com.nmts.agency.service.AgencyService;
import com.nmts.agency.util.RoleGuard;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/agency")
public class AgencyController {

    private final AgencyService agencyService;
    private final RoleGuard roleGuard;

    public AgencyController(AgencyService agencyService, RoleGuard roleGuard) {
        this.agencyService = agencyService;
        this.roleGuard = roleGuard;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AgencyResponseDTO>> registerAgency(
            @Valid @RequestBody CreateAgencyDTO dto,
            HttpServletRequest request) {
        roleGuard.requireRole(request, "MINING_AGENCY");
        UUID ownerId = roleGuard.extractUserId(request);
        AgencyResponseDTO response = agencyService.registerAgency(ownerId, dto);
        return new ResponseEntity<>(ApiResponse.success("Agency registered successfully", response), HttpStatus.CREATED);
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<AgencyResponseDTO>> getMyAgency(HttpServletRequest request) {
        roleGuard.requireRole(request, "MINING_AGENCY");
        UUID ownerId = roleGuard.extractUserId(request);
        AgencyResponseDTO response = agencyService.getAgencyByOwner(ownerId);
        return ResponseEntity.ok(ApiResponse.success("Agency fetched successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AgencyResponseDTO>> getAgencyById(@PathVariable UUID id) {
        AgencyResponseDTO response = agencyService.getAgencyById(id);
        return ResponseEntity.ok(ApiResponse.success("Agency fetched successfully", response));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<AgencyResponseDTO>>> getAllAgencies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) OperationStatus status,
            HttpServletRequest request) {
        roleGuard.requireRole(request, "ADMIN");
        Pageable pageable = PageRequest.of(page, size);
        Page<AgencyResponseDTO> response = agencyService.getAllAgencies(status, pageable);
        return ResponseEntity.ok(ApiResponse.success("Agencies fetched successfully", response));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AgencyResponseDTO>> updateAgencyStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateAgencyStatusDTO dto,
            HttpServletRequest request) {
        roleGuard.requireAnyRole(request, "GOV_OFFICER", "ADMIN");
        AgencyResponseDTO response = agencyService.updateAgencyStatus(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Agency status updated successfully", response));
    }
}
