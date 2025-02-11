package com.thaidot.profile.service;

import com.thaidot.profile.dto.PageResponse;
import com.thaidot.profile.dto.request.FollowRequest;
import com.thaidot.profile.dto.response.UserProfileCreationResponse;
import com.thaidot.profile.entity.UserProfile;
import com.thaidot.profile.mapper.UserProfileMapper;
import com.thaidot.profile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final CheckFollowService checkFollowService;

    public void followUser(FollowRequest followRequest) {
        checkFollowService.checkFollowRequest(followRequest);
        checkFollowService.checkForFollowYourSelf(followRequest);
        checkFollowService.checkAlreadyFollowed(followRequest);

        userProfileRepository.followUser(followRequest.getFollowerID(), followRequest.getFolloweeID());

    }

    public void unfollowUser(FollowRequest followRequest) {
        checkFollowService.checkFollowRequest(followRequest);
        checkFollowService.checkForUnfollow(followRequest);
        userProfileRepository.unfollowUser(followRequest.getFollowerID(), followRequest.getFolloweeID());
    }

    public PageResponse<UserProfileCreationResponse> getFollowers(String userID, int page, int size) {
        checkFollowService.checkOwner(userID);
        //Make pagination config here
        Sort sort = Sort.by(Sort.Order.desc("firstName"));
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<UserProfile> followers = userProfileRepository.getFollowers(userID, pageable);
        return PageResponse.<UserProfileCreationResponse>builder()
                .pageSize(followers.getSize())
                .totalElements(followers.getTotalElements())
                .totalPages(followers.getTotalPages())
                .currentPage(page)
                .data(followers.getContent().stream().map(userProfileMapper::toUserProfileResponse).toList())
                .build();
    }

    public PageResponse<UserProfileCreationResponse> getFollowing(String userID, int page, int size) {
        checkFollowService.checkOwner(userID);
        Sort sort = Sort.by(Sort.Order.desc("firstName"));
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<UserProfile> following = userProfileRepository.getFollowing(userID, pageable);

        return PageResponse.<UserProfileCreationResponse>builder()
                .pageSize(following.getSize())
                .totalElements(following.getTotalElements())
                .totalPages(following.getTotalPages())
                .currentPage(page)
                .data(following.getContent().stream().map(userProfileMapper::toUserProfileResponse).toList())
                .build();
    }

}
