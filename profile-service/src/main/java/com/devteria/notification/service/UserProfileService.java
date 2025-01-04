package com.devteria.notification.service;

import com.devteria.notification.dto.request.UserProfileCreationRequest;
import com.devteria.notification.dto.response.UserProfileCreationResponse;
import com.devteria.notification.entity.UserProfile;
import com.devteria.notification.mapper.UserProfileMapper;
import com.devteria.notification.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileCreationResponse createUserProfile(UserProfileCreationRequest userProfileCreationRequest) {
        UserProfile userProfile = userProfileMapper.toUserProfile(userProfileCreationRequest);

        userProfile = userProfileRepository.save(userProfile);

        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileCreationResponse getUserProfile(String id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("User profile not found"));

        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfileCreationResponse> getAllProfiles() {
        List<UserProfile> profiles = userProfileRepository.findAll();

        return profiles.stream().map(userProfileMapper::toUserProfileResponse).toList();
    }
}
