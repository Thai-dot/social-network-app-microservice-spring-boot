package com.devteria.profile.controller;

import com.devteria.profile.dto.request.ApiResponse;
import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.response.UserProfileCreationResponse;
import com.devteria.profile.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InternalUserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/internal/user-profile")
    ResponseEntity<ApiResponse<UserProfileCreationResponse>>  createUserProfile(@Valid @RequestBody  UserProfileCreationRequest request) {
      log.error("request {}: ", request);
        UserProfileCreationResponse userProfile = userProfileService.createUserProfile(request);
        ApiResponse<UserProfileCreationResponse> response = new ApiResponse<>(
                1000,
                "Created user successfully",
                userProfile
        );

        return new ResponseEntity<>(response,HttpStatus.OK);

    }
}
