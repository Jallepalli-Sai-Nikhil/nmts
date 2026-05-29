package com.nmts.agency.controller;

import com.nmts.agency.dto.ApiResponse;
import com.nmts.agency.dto.CreateListingDTO;
import com.nmts.agency.dto.ListingResponseDTO;
import com.nmts.agency.dto.UpdateListingDTO;
import com.nmts.agency.service.ListingService;
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
@RequestMapping("/agency/listings")
public class ListingController {

    private final ListingService listingService;
    private final RoleGuard roleGuard;

    public ListingController(ListingService listingService, RoleGuard roleGuard) {
        this.listingService = listingService;
        this.roleGuard = roleGuard;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ListingResponseDTO>> createListing(
            @Valid @RequestBody CreateListingDTO dto,
            HttpServletRequest request) {
        roleGuard.requireRole(request, "MINING_AGENCY");
        UUID ownerId = roleGuard.extractUserId(request);
        ListingResponseDTO response = listingService.createListing(ownerId, dto);
        return new ResponseEntity<>(ApiResponse.success("Listing created successfully", response), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ListingResponseDTO>>> getActiveListings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String metalName) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ListingResponseDTO> response = listingService.getActiveListings(metalName, pageable);
        return ResponseEntity.ok(ApiResponse.success("Active listings fetched successfully", response));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ListingResponseDTO>> searchListings(@RequestParam String metalName) {
        // Internal endpoint for search-catalog-service Feign call
        return ResponseEntity.ok(listingService.searchListings(metalName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ListingResponseDTO>> getListingById(@PathVariable UUID id) {
        ListingResponseDTO response = listingService.getListingById(id);
        return ResponseEntity.ok(ApiResponse.success("Listing fetched successfully", response));
    }

    @GetMapping("/my/listings")
    public ResponseEntity<ApiResponse<List<ListingResponseDTO>>> getMyListings(HttpServletRequest request) {
        roleGuard.requireRole(request, "MINING_AGENCY");
        UUID ownerId = roleGuard.extractUserId(request);
        List<ListingResponseDTO> response = listingService.getMyListings(ownerId);
        return ResponseEntity.ok(ApiResponse.success("Your listings fetched successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ListingResponseDTO>> updateListing(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateListingDTO dto,
            HttpServletRequest request) {
        roleGuard.requireRole(request, "MINING_AGENCY");
        UUID ownerId = roleGuard.extractUserId(request);
        ListingResponseDTO response = listingService.updateListing(ownerId, id, dto);
        return ResponseEntity.ok(ApiResponse.success("Listing updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteListing(
            @PathVariable UUID id,
            HttpServletRequest request) {
        roleGuard.requireRole(request, "MINING_AGENCY");
        UUID ownerId = roleGuard.extractUserId(request);
        listingService.deleteListing(ownerId, id);
        return ResponseEntity.ok(ApiResponse.success("Listing deleted successfully", null));
    }
}
