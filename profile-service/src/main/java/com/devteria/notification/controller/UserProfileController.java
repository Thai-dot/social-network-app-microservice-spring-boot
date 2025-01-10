package com.devteria.notification.controller;

import com.devteria.notification.dto.request.ApiResponse;
import com.devteria.notification.dto.response.UserProfileCreationResponse;
import com.devteria.notification.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user-profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;


    @GetMapping("/{profileID}")
    ResponseEntity<ApiResponse<UserProfileCreationResponse>> getUserProfile(@PathVariable String profileID) {
        UserProfileCreationResponse userProfile = userProfileService.getUserProfile(profileID);

        ApiResponse<UserProfileCreationResponse> response = new ApiResponse<>(
                1000,
                "User Profile fetched successfully",
                userProfile
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<ApiResponse<List<UserProfileCreationResponse>>> getAllProfiles() {
        ApiResponse<List<UserProfileCreationResponse>> response = new ApiResponse<>(
                1000,
                "User Profiles fetched successfully",
                userProfileService.getAllProfiles()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/id/{userId}")
    ResponseEntity<ApiResponse<UserProfileCreationResponse>> getProfileByUserId(@PathVariable String userId) {
        UserProfileCreationResponse userProfile = userProfileService.getProfileByUserId(userId);

        ApiResponse<UserProfileCreationResponse> response = new ApiResponse<>(
                1000,
                "User Profile fetched successfully",
                userProfile
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
