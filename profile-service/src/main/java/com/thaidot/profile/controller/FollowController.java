package com.thaidot.profile.controller;

import com.thaidot.profile.dto.ApiResponse;
import com.thaidot.profile.dto.PageResponse;
import com.thaidot.profile.dto.request.FollowRequest;
import com.thaidot.profile.dto.response.UserProfileCreationResponse;
import com.thaidot.profile.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping()
    public ApiResponse<Void> followUser(@RequestBody FollowRequest followRequest) {
        followService.followUser(followRequest);
        return ApiResponse.<Void>builder()
                .message("User followed successfully")
                .build();
    }

    //get all followers of a user
    @GetMapping("/followers/{userID}")
    public ApiResponse<PageResponse<UserProfileCreationResponse>> getFollowers(
            @PathVariable String userID,
            @PageableDefault(page = 1, size = 10) Pageable pageable) {
        PageResponse<UserProfileCreationResponse> followers = followService.getFollowers(userID,
                pageable.getPageNumber(), pageable.getPageSize());
        return ApiResponse.<PageResponse<UserProfileCreationResponse>>builder()
                .message("Followers retrieved successfully")
                .result(followers)
                .build();
    }

    //get all followings of a user
    @GetMapping("/following/{userID}")
    public ApiResponse<PageResponse<UserProfileCreationResponse>> getFollowing(
            @PathVariable String userID,
            @PageableDefault(page = 1, size = 10) Pageable pageable) {
        PageResponse<UserProfileCreationResponse> following = followService.getFollowing(userID,
                pageable.getPageNumber(), pageable.getPageSize());
        return ApiResponse.<PageResponse<UserProfileCreationResponse>>builder()
                .message("Following retrieved successfully")
                .result(following)
                .build();
    }

    @DeleteMapping("/unfollow")
    public ApiResponse<Void> unfollowUser(@RequestBody FollowRequest followRequest) {
        followService.unfollowUser(followRequest);
        return ApiResponse.<Void>builder()
                .message("User unfollowed successfully")
                .build();
    }
}
