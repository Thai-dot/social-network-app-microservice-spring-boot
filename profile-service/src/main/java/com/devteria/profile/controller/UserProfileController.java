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
@RequestMapping("/user-profile")
@RequiredArgsConstructor
public class UserProfileController {

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

    @GetMapping("/{profileID}")
    ResponseEntity<ApiResponse<UserProfileCreationResponse>>  getUserProfile(@PathVariable String profileID) {
        UserProfileCreationResponse userProfile= userProfileService.getUserProfile(profileID);

        ApiResponse<UserProfileCreationResponse> response = new ApiResponse<>(
                "User Profile fetched successfully",
                HttpStatus.OK.value(),
                userProfile
        );

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
