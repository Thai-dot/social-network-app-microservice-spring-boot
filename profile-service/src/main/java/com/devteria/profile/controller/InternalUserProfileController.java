package com.devteria.profile.controller;

import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.response.ApiResponse;
import com.devteria.profile.dto.response.UserProfileCreationResponse;
import com.devteria.profile.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/user-profile")
@RequiredArgsConstructor
public class InternalUserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/")
    ResponseEntity<ApiResponse<UserProfileCreationResponse>>  createUserProfile(@Valid @RequestBody  UserProfileCreationRequest request) {
        UserProfileCreationResponse userProfile = userProfileService.createUserProfile(request);
        ApiResponse<UserProfileCreationResponse> response = new ApiResponse<>(
                "User Profile created successfully",
                HttpStatus.OK.value(),
                userProfile
        );

        return new ResponseEntity<>(response,HttpStatus.OK);

    }
}
