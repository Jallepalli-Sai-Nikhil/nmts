package com.nmts.agency.controller;

import com.nmts.agency.dto.ApiResponse;
import com.nmts.agency.dto.PurchaseRequestDTO;
import com.nmts.agency.dto.PurchaseResponseDTO;
import com.nmts.agency.dto.RejectRequestDTO;
import com.nmts.agency.entity.PurchaseStatus;
import com.nmts.agency.service.PurchaseRequestService;
import com.nmts.agency.util.RoleGuard;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agency/purchase-requests")
public class PurchaseRequestController {

    private final PurchaseRequestService requestService;
    private final RoleGuard roleGuard;

    public PurchaseRequestController(PurchaseRequestService requestService, RoleGuard roleGuard) {
        this.requestService = requestService;
        this.roleGuard = roleGuard;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseResponseDTO>> createRequest(
            @Valid @RequestBody PurchaseRequestDTO dto,
            HttpServletRequest request) {
        roleGuard.requireRole(request, "CUSTOMER");
        UUID customerId = roleGuard.extractUserId(request);
        String customerName = request.getHeader("X-User-Email"); // Using email as name if name not available in headers
        PurchaseResponseDTO response = requestService.createRequest(customerId, customerName, dto);
        return new ResponseEntity<>(ApiResponse.success("Purchase request sent successfully", response), HttpStatus.CREATED);
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<PurchaseResponseDTO>>> getMyRequests(HttpServletRequest request) {
        roleGuard.requireRole(request, "CUSTOMER");
        UUID customerId = roleGuard.extractUserId(request);
        List<PurchaseResponseDTO> response = requestService.getMyRequests(customerId);
        return ResponseEntity.ok(ApiResponse.success("Your purchase requests fetched successfully", response));
    }

    @GetMapping("/incoming")
    public ResponseEntity<ApiResponse<List<PurchaseResponseDTO>>> getIncomingRequests(HttpServletRequest request) {
        roleGuard.requireRole(request, "MINING_AGENCY");
        UUID ownerId = roleGuard.extractUserId(request);
        List<PurchaseResponseDTO> response = requestService.getIncomingRequests(ownerId, PurchaseStatus.PENDING);
        return ResponseEntity.ok(ApiResponse.success("Incoming PENDING requests fetched successfully", response));
    }

    @GetMapping("/incoming/all")
    public ResponseEntity<ApiResponse<Page<PurchaseResponseDTO>>> getAllIncomingRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        roleGuard.requireRole(request, "MINING_AGENCY");
        UUID ownerId = roleGuard.extractUserId(request);
        Pageable pageable = PageRequest.of(page, size);
        Page<PurchaseResponseDTO> response = requestService.getAllAgencyRequests(ownerId, pageable);
        return ResponseEntity.ok(ApiResponse.success("All incoming requests fetched successfully", response));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<Void>> approveRequest(
            @PathVariable UUID id,
            HttpServletRequest request) {
        roleGuard.requireRole(request, "MINING_AGENCY");
        UUID ownerId = roleGuard.extractUserId(request);
        requestService.approveRequest(ownerId, id);
        return ResponseEntity.ok(ApiResponse.success("Purchase request approved successfully", null));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectRequest(
            @PathVariable UUID id,
            @Valid @RequestBody RejectRequestDTO dto,
            HttpServletRequest request) {
        roleGuard.requireRole(request, "MINING_AGENCY");
        UUID ownerId = roleGuard.extractUserId(request);
        requestService.rejectRequest(ownerId, id, dto);
        return ResponseEntity.ok(ApiResponse.success("Purchase request rejected successfully", null));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<PurchaseResponseDTO>>> getAllRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        roleGuard.requireRole(request, "ADMIN");
        Pageable pageable = PageRequest.of(page, size);
        Page<PurchaseResponseDTO> response = requestService.getAllRequests(pageable);
        return ResponseEntity.ok(ApiResponse.success("Platform-wide requests fetched successfully", response));
    }
}
