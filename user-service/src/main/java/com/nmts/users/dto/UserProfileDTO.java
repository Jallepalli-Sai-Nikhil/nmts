package com.nmts.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
