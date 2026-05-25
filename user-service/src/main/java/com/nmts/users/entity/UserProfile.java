package com.nmts.users.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


/*
 todo: User responsibilities:
    - Manage personal information (name, email, phone, address)
    - View and update profile details
    - View order history and status
    - see what get approved and what not
    - view previous orders and their details
    - Purchase history belongs to user-service
 */


@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
