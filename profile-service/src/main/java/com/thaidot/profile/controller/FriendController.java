package com.thaidot.profile.controller;

import com.thaidot.profile.dto.ApiResponse;
import com.thaidot.profile.dto.PageResponse;
import com.thaidot.profile.dto.response.UserProfileCreationResponse;
import com.thaidot.profile.service.FriendService;
import com.thaidot.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {
    private final FriendService friendService;

    @GetMapping("/list-friend")
    ApiResponse<PageResponse<UserProfileCreationResponse>> getListFriendOfUser(
            @PageableDefault(page = 1, size = 10) Pageable pageable
    ) {

        return ApiResponse.<PageResponse<UserProfileCreationResponse>>builder()
                .message("List of friends retrieved successfully")
                .result(friendService.getFriends(pageable.getPageNumber(), pageable.getPageSize()))
                .build();
    }

    @DeleteMapping("/unfriend/{whoID}")
    ApiResponse<Void> unfriend(@PathVariable String whoID) {
        friendService.unfriend(whoID);

        return ApiResponse.<Void>builder()
                .message("Unfriended successfully")
                .build();
    }
}
