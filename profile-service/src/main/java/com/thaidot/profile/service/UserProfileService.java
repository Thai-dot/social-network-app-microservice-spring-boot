package com.thaidot.profile.service;

import com.thaidot.profile.dto.request.UserProfileCreationRequest;
import com.thaidot.profile.dto.response.UserProfileCreationResponse;
import com.thaidot.profile.entity.UserProfile;
import com.thaidot.profile.mapper.UserProfileMapper;
import com.thaidot.profile.repository.UserProfileRepository;
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
    private final KafkaProducer kafkaProducer;

    public UserProfileCreationResponse createUserProfile(UserProfileCreationRequest userProfileCreationRequest) {
        UserProfile userProfile = userProfileMapper.toUserProfile(userProfileCreationRequest);

        userProfile = userProfileRepository.save(userProfile);
        kafkaProducer.sendAddProfileElastic(userProfileMapper.toProfileEvent(userProfile));

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

    public UserProfileCreationResponse getProfileByUserId(String userID) {

            UserProfile userProfile = userProfileRepository.findByUserID(userID).orElseThrow(() -> new RuntimeException("User profile not found"));
            return userProfileMapper.toUserProfileResponse(userProfile);

    }


}
