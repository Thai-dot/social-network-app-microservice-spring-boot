package com.thaidot.profile.service;

import com.thaidot.profile.dto.request.FollowRequest;
import com.thaidot.profile.exception.AppException;
import com.thaidot.profile.exception.ErrorCode;
import com.thaidot.profile.repository.UserProfileRepository;
import com.thaidot.profile.utils.GetJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckFollowService {
    private final UserProfileRepository userProfileRepository;
    private final GetJWT getJWT;

    public void checkFollowRequest(FollowRequest followRequest) {
        userProfileRepository.findByUserID(followRequest.getFollowerID()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userProfileRepository.findByUserID(followRequest.getFolloweeID()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public void checkForFollowYourSelf(FollowRequest followRequest) {

        String userID = checkOwner(followRequest.getFollowerID());
        if (userID.equals(followRequest.getFolloweeID())) {
            throw new AppException(ErrorCode.CANNOT_FOLLOW_YOURSELF);
        }
    }

    public void checkAlreadyFollowed(FollowRequest followRequest) {
        if (userProfileRepository.isUserFollowingOtherUser(followRequest.getFollowerID(), followRequest.getFolloweeID())) {
            throw new AppException(ErrorCode.ALREADY_FOLLOWING);
        }
    }

    public void checkForUnfollow(FollowRequest followRequest) {
        if (!userProfileRepository.isUserFollowingOtherUser(followRequest.getFollowerID(), followRequest.getFolloweeID())) {
            throw new AppException(ErrorCode.ALREADY_UNFOLLOWING);
        }
    }

    public String checkOwner(String checkUserID) {
        String userID = getJWT.getSubject();

        if (!checkUserID.equals(userID)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return userID;
    }
}
