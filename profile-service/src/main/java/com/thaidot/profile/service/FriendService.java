package com.thaidot.profile.service;

import com.thaidot.profile.dto.PageResponse;
import com.thaidot.profile.dto.request.FollowRequest;
import com.thaidot.profile.dto.response.UserProfileCreationResponse;
import com.thaidot.profile.entity.UserProfile;
import com.thaidot.profile.exception.AppException;
import com.thaidot.profile.exception.ErrorCode;
import com.thaidot.profile.mapper.UserProfileMapper;
import com.thaidot.profile.repository.UserProfileRepository;
import com.thaidot.profile.utils.GetJWT;
import com.thaidot.profile.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserProfileRepository userProfileRepository;
    private final FollowService followService;
    private final GetJWT getJWT;
    private final PaginationUtil paginationUtil;
    private final UserProfileMapper userProfileMapper;

    public void unfriend(String whoID) {
        String userID = getJWT.getSubject();

        if (!userProfileRepository.areUsersFriends(userID, whoID)) {
            throw new AppException(ErrorCode.FRIENDSHIP_NOT_EXISTED);
        }
        followService.unfollowUser(FollowRequest.builder().followerID(userID).followeeID(whoID).build());
        followService.unfollowUser(FollowRequest.builder().followerID(whoID).followeeID(userID).build());
        userProfileRepository.unfriendUsers(userID, whoID);

    }


    public PageResponse<UserProfileCreationResponse> getFriends( int page, int size) {
        String userID = getJWT.getSubject();
        //Make pagination config here
        Pageable pageable = PaginationUtil.getPageable(page,size);
        Page<UserProfile> friends = userProfileRepository.getFriends(userID, pageable);

        return paginationUtil.getPageResponse(friends, friends.getContent().stream().map(userProfileMapper::toUserProfileResponse).toList());
    }
}
