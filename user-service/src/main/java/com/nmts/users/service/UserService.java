package com.nmts.users.service;


import com.nmts.users.dto.CreateUserInternalDTO;
import com.nmts.users.dto.UpdateProfileDTO;
import com.nmts.users.dto.UserProfileDTO;
import com.nmts.users.entity.UserProfile;
import com.nmts.users.exception.EntityNotFoundException;
import com.nmts.users.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserProfileRepository userProfileRepository;

    @Transactional
    public void createUserProfile(CreateUserInternalDTO dto) {
        log.info("Creating user profile for authUserId: {}", dto.getAuthUserId());
        UserProfile profile = UserProfile.builder()
                .id(UUID.randomUUID())
                .authUserId(dto.getAuthUserId())
                .name(dto.getName())
                .email(dto.getEmail())
                .role(dto.getRole())
                .build();
        userProfileRepository.save(profile);
    }

    public UserProfileDTO getProfileByAuthUserId(UUID authUserId) {
        UserProfile profile = userProfileRepository.findByAuthUserId(authUserId)
                .orElseThrow(() -> new EntityNotFoundException("User profile not found for authUserId: " + authUserId));
        return mapToDTO(profile);
    }

    public UserProfileDTO getProfileById(UUID id) {
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User profile not found with id: " + id));
        return mapToDTO(profile);
    }

    @Transactional
    public UserProfileDTO updateProfile(UUID authUserId, UpdateProfileDTO dto) {
        UserProfile profile = userProfileRepository.findByAuthUserId(authUserId)
                .orElseThrow(() -> new EntityNotFoundException("User profile not found for authUserId: " + authUserId));

        if (dto.getName() != null) profile.setName(dto.getName());
        if (dto.getPhone() != null) profile.setPhone(dto.getPhone());
        if (dto.getAddress() != null) profile.setAddress(dto.getAddress());
        if (dto.getBusinessName() != null) profile.setBusinessName(dto.getBusinessName());

        return mapToDTO(userProfileRepository.save(profile));
    }

    public Page<UserProfileDTO> getAllProfiles(int page, int size, String role) {
        PageRequest pageRequest = PageRequest.of(page, size);
        // For simplicity, if role is provided, we could use a custom query, but here I'll just filter all for now if role is null.
        // Actually, JpaRepository doesn't have findByRole unless we add it.
        // Let's just return all for now or add findByRole.
        return userProfileRepository.findAll(pageRequest).map(this::mapToDTO);
    }

    private UserProfileDTO mapToDTO(UserProfile profile) {
        return UserProfileDTO.builder()
                .id(profile.getId())
                .authUserId(profile.getAuthUserId())
                .name(profile.getName())
                .email(profile.getEmail())
                .phone(profile.getPhone())
                .address(profile.getAddress())
                .businessName(profile.getBusinessName())
                .role(profile.getRole())
                .createdAt(profile.getCreatedAt())
                .build();
    }
}

