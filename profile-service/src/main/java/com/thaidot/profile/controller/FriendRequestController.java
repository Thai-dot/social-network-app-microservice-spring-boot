package com.thaidot.profile.controller;

import com.thaidot.profile.dto.ApiResponse;
import com.thaidot.profile.dto.PageResponse;
import com.thaidot.profile.dto.request.FriendRequestRequest;
import com.thaidot.profile.dto.response.FriendRequestResponse;
import com.thaidot.profile.dto.response.FriendRequestResponseWithUserProfile;
import com.thaidot.profile.dto.response.UserProfileCreationResponse;
import com.thaidot.profile.entity.UserProfile;
import com.thaidot.profile.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend-request")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @GetMapping("/list-request")
    public ApiResponse<PageResponse<FriendRequestResponseWithUserProfile>> getListFriendOfUser(
            @PageableDefault(page = 1, size = 10) Pageable page
    ) {
        return ApiResponse.<PageResponse<FriendRequestResponseWithUserProfile>>builder()
                .message("List of friends requests retrieved successfully")
                .result(friendRequestService.getListRequestFriend(page.getPageNumber(), page.getPageSize()))
                .build();
    }

    @PostMapping("/send-request")
    public ApiResponse<FriendRequestResponse> sendFriendRequest(@RequestBody FriendRequestRequest friendRequestRequest) {
        return ApiResponse.<FriendRequestResponse>builder()
                .message("Friend request sent successfully")
                .result(friendRequestService.sendFriendRequest(friendRequestRequest))
                .build();
    }

    @PatchMapping("/accept-request")
    public ApiResponse<Void> acceptFriendRequest(@RequestBody FriendRequestRequest friendRequestRequest) {
        friendRequestService.acceptFriendRequest(friendRequestRequest);
        return ApiResponse.<Void>builder()
                .message("Friend request accepted successfully")
                .build();
    }

    @DeleteMapping("/reject-request")
    public ApiResponse<Void> rejectFriendRequest(@RequestBody FriendRequestRequest friendRequestRequest) {
        friendRequestService.removeFriendRequest(friendRequestRequest);
        return ApiResponse.<Void>builder()
                .message("Friend deleted successfully")
                .build();
    }
}
