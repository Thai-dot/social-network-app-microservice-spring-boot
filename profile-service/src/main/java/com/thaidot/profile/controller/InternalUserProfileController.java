package com.thaidot.profile.controller;

import com.thaidot.profile.dto.ApiResponse;
import com.thaidot.profile.dto.request.UserProfileCreationRequest;
import com.thaidot.profile.dto.response.UserProfileCreationResponse;
import com.thaidot.profile.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InternalUserProfileController {

    private final UserProfileService userProfileService;

    @Value("${avatar.default}")
    private String defaultAvatarUrl;

    @PostMapping("/internal/user-profile")
    ResponseEntity<ApiResponse<UserProfileCreationResponse>>  createUserProfile(@Valid @RequestBody  UserProfileCreationRequest request) {
        UserProfileCreationResponse userProfile = userProfileService.createUserProfile(request);
        userProfile.setAvatarUrl(defaultAvatarUrl);
        ApiResponse<UserProfileCreationResponse> response = new ApiResponse<>(
                1000,
                "Created user successfully",
                userProfile
        );

        return new ResponseEntity<>(response,HttpStatus.OK);

    }
}
