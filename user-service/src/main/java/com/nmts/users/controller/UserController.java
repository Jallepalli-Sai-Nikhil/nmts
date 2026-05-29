package com.nmts.users.controller;


import com.nmts.users.dto.*;
import com.nmts.users.service.PurchaseHistoryService;
import com.nmts.users.service.UserService;
import com.nmts.users.util.RoleGuard;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PurchaseHistoryService purchaseHistoryService;
    private final RoleGuard roleGuard;

    public UserController(UserService userService, PurchaseHistoryService purchaseHistoryService, RoleGuard roleGuard) {
        this.userService = userService;
        this.purchaseHistoryService = purchaseHistoryService;
        this.roleGuard = roleGuard;
    }

    @PostMapping("/internal/create")
    public ResponseEntity<Void> createUserProfile(@RequestBody CreateUserInternalDTO dto) {
        userService.createUserProfile(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getOwnProfile(HttpServletRequest request) {
        UUID authUserId = roleGuard.extractUserId(request);
        UserProfileDTO profile = userService.getProfileByAuthUserId(authUserId);
        return ResponseEntity.ok(ApiResponse.success("Profile fetched successfully", profile));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileDTO>> updateProfile(
            HttpServletRequest request,
            @Valid @RequestBody UpdateProfileDTO dto) {
        UUID authUserId = roleGuard.extractUserId(request);
        UserProfileDTO profile = userService.updateProfile(authUserId, dto);
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", profile));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse<UserProfileDTO>> getProfileById(
            HttpServletRequest request,
            @PathVariable UUID userId) {
        roleGuard.requireRole(request, "ADMIN");
        UserProfileDTO profile = userService.getProfileById(userId);
        return ResponseEntity.ok(ApiResponse.success("Profile fetched successfully", profile));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<UserProfileDTO>>> getAllProfiles(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String role) {
        roleGuard.requireRole(request, "ADMIN");
        Page<UserProfileDTO> profiles = userService.getAllProfiles(page, size, role);
        return ResponseEntity.ok(ApiResponse.success("Profiles fetched successfully", profiles));
    }

    @GetMapping("/purchase-history")
    public ResponseEntity<ApiResponse<List<PurchaseHistoryDTO>>> getPurchaseHistory(HttpServletRequest request) {
        roleGuard.requireRole(request, "CUSTOMER");
        UUID customerId = roleGuard.extractUserId(request);
        List<PurchaseHistoryDTO> history = purchaseHistoryService.getPurchaseHistory(customerId);
        return ResponseEntity.ok(ApiResponse.success("Purchase history fetched successfully", history));
    }
}

