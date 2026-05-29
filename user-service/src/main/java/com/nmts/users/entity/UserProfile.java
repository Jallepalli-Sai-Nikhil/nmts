package com.nmts.users.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private UUID authUserId;

    private String name;
    private String email;
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String businessName;
    private String role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserProfile() {}

    public UserProfile(UUID id, UUID authUserId, String name, String email, String phone, String address, String businessName, String role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.authUserId = authUserId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.businessName = businessName;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserProfileBuilder builder() {
        return new UserProfileBuilder();
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

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        java.util.Objects.requireNonNull(o); // Simple check
        UserProfile that = (UserProfile) o;
        return java.util.Objects.equals(id, that.id) &&
                java.util.Objects.equals(authUserId, that.authUserId) &&
                java.util.Objects.equals(name, that.name) &&
                java.util.Objects.equals(email, that.email) &&
                java.util.Objects.equals(phone, that.phone) &&
                java.util.Objects.equals(address, that.address) &&
                java.util.Objects.equals(businessName, that.businessName) &&
                java.util.Objects.equals(role, that.role) &&
                java.util.Objects.equals(createdAt, that.createdAt) &&
                java.util.Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, authUserId, name, email, phone, address, businessName, role, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", authUserId=" + authUserId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", businessName='" + businessName + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static class UserProfileBuilder {
        private UUID id;
        private UUID authUserId;
        private String name;
        private String email;
        private String phone;
        private String address;
        private String businessName;
        private String role;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        UserProfileBuilder() {}

        public UserProfileBuilder id(UUID id) { this.id = id; return this; }
        public UserProfileBuilder authUserId(UUID authUserId) { this.authUserId = authUserId; return this; }
        public UserProfileBuilder name(String name) { this.name = name; return this; }
        public UserProfileBuilder email(String email) { this.email = email; return this; }
        public UserProfileBuilder phone(String phone) { this.phone = phone; return this; }
        public UserProfileBuilder address(String address) { this.address = address; return this; }
        public UserProfileBuilder businessName(String businessName) { this.businessName = businessName; return this; }
        public UserProfileBuilder role(String role) { this.role = role; return this; }
        public UserProfileBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public UserProfileBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public UserProfile build() {
            return new UserProfile(id, authUserId, name, email, phone, address, businessName, role, createdAt, updatedAt);
        }
    }
}
