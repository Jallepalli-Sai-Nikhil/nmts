package com.nmts.users.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserProfileDTO {
    private UUID id;
    private UUID authUserId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String businessName;
    private String role;
    private LocalDateTime createdAt;

    public UserProfileDTO() {}

    public UserProfileDTO(UUID id, UUID authUserId, String name, String email, String phone, String address, String businessName, String role, LocalDateTime createdAt) {
        this.id = id;
        this.authUserId = authUserId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.businessName = businessName;
        this.role = role;
        this.createdAt = createdAt;
    }

    public static UserProfileDTOBuilder builder() {
        return new UserProfileDTOBuilder();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getAuthUserId() { return authUserId; }
    public void setAuthUserId(UUID authUserId) { this.authUserId = authUserId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static class UserProfileDTOBuilder {
        private UUID id;
        private UUID authUserId;
        private String name;
        private String email;
        private String phone;
        private String address;
        private String businessName;
        private String role;
        private LocalDateTime createdAt;

        UserProfileDTOBuilder() {}

        public UserProfileDTOBuilder id(UUID id) { this.id = id; return this; }
        public UserProfileDTOBuilder authUserId(UUID authUserId) { this.authUserId = authUserId; return this; }
        public UserProfileDTOBuilder name(String name) { this.name = name; return this; }
        public UserProfileDTOBuilder email(String email) { this.email = email; return this; }
        public UserProfileDTOBuilder phone(String phone) { this.phone = phone; return this; }
        public UserProfileDTOBuilder address(String address) { this.address = address; return this; }
        public UserProfileDTOBuilder businessName(String businessName) { this.businessName = businessName; return this; }
        public UserProfileDTOBuilder role(String role) { this.role = role; return this; }
        public UserProfileDTOBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public UserProfileDTO build() {
            return new UserProfileDTO(id, authUserId, name, email, phone, address, businessName, role, createdAt);
        }
    }
}
