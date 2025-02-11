package com.thaidot.profile.service;

import com.thaidot.profile.dto.PageResponse;
import com.thaidot.profile.dto.request.FriendRequestRequest;
import com.thaidot.profile.dto.response.FriendRequestResponse;
import com.thaidot.profile.dto.response.FriendRequestResponseWithUserProfile;
import com.thaidot.profile.entity.FriendRequest;
import com.thaidot.profile.entity.UserProfile;
import com.thaidot.profile.exception.AppException;
import com.thaidot.profile.exception.ErrorCode;
import com.thaidot.profile.mapper.FriendRequestMapper;
import com.thaidot.profile.repository.FriendRequestRepository;
import com.thaidot.profile.repository.UserProfileRepository;
import com.thaidot.profile.utils.GetJWT;
import com.thaidot.profile.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRequestService {


    private final UserProfileRepository userProfileRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRequestMapper friendRequestMapper;
    private final GetJWT getJWT;
    private final PaginationUtil paginationUtil;
    private final UserProfileService userProfileService;

    public PageResponse<FriendRequestResponseWithUserProfile> getListRequestFriend(int page, int size) {
        String userID = getJWT.getSubject();
        userProfileRepository.findByUserID(userID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Pageable pageable = PaginationUtil.getPageable(page, size);
        Page<FriendRequest> friends = friendRequestRepository.findByReceiverID(userID, pageable);
        List<FriendRequestResponseWithUserProfile> friendRequestResponses = friends.getContent().stream()
                .map((item) -> {
                    FriendRequestResponseWithUserProfile friend = friendRequestMapper.toFriendRequestResponseWithUserProfile(item);

                    friend.setUserProfile(userProfileService.getProfileByUserId(item.getSenderID()));
                    return friend;
                }).toList();

        return paginationUtil.getPageResponse(friends, friendRequestResponses);
    }

    public FriendRequestResponse sendFriendRequest(FriendRequestRequest friendRequestRequest) {
        if (checkFriendRequestExist(friendRequestRequest)) {
            throw new AppException(ErrorCode.FRIEND_REQUEST_EXISTED);
        }
        // Check if the users exist
        List<UserProfile> users = getSenderAndReceiver(friendRequestRequest);
        UserProfile sender = users.get(0);
        UserProfile receiver = users.get(1);

        if (sender == null || receiver == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        if (userProfileRepository.areUsersFriends(sender.getUserID(), receiver.getUserID())) {
            throw new AppException(ErrorCode.ALREADY_FRIENDS);
        }

        // Create and save friend request
        FriendRequest request = FriendRequest.builder()
                .senderID(sender.getUserID())
                .receiverID(receiver.getUserID())
                .accepted(false)
                .requestDate(Instant.now())
                .build();
        var savedFriendRequest = friendRequestRepository.save(request);
        return friendRequestMapper.toFriendRequestResponse(savedFriendRequest);
    }

    public void acceptFriendRequest(FriendRequestRequest friendRequestRequest) {
        String userID = getJWT.getSubject();
        if (!userID.equals(friendRequestRequest.getReceiverID())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        FriendRequest friendRequest = friendRequestRepository.findBySenderIDAndReceiverID(friendRequestRequest.getSenderID(), friendRequestRequest.getReceiverID())
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        if (friendRequest.isAccepted()) {
            throw new AppException(ErrorCode.ALREADY_ACCEPTED);
        }

        userProfileRepository.addFriend(friendRequest.getSenderID(), friendRequest.getReceiverID());

        userProfileRepository.followUser(friendRequest.getSenderID(), friendRequest.getReceiverID());

        userProfileRepository.followUser(friendRequest.getReceiverID(), friendRequest.getSenderID());

        removeFriendRequest(friendRequestRequest);
    }

    public void removeFriendRequest(FriendRequestRequest friendRequestRequest) {
        String userID = getJWT.getSubject();
        if (!userID.equals(friendRequestRequest.getReceiverID())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        friendRequestRepository.deleteBySenderIDAndReceiverID(friendRequestRequest.getSenderID(), friendRequestRequest.getReceiverID());
    }

    private List<UserProfile> getSenderAndReceiver(FriendRequestRequest friendRequestRequest) {
        UserProfile sender =
                userProfileRepository.findByUserID(friendRequestRequest.getSenderID()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserProfile receiver =
                userProfileRepository.findByUserID(friendRequestRequest.getReceiverID()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return List.of(sender, receiver);
    }

    private boolean checkFriendRequestExist(FriendRequestRequest friendRequestRequest) {
        return friendRequestRepository.findBySenderIDAndReceiverID(friendRequestRequest.getSenderID(),
                friendRequestRequest.getReceiverID()).isPresent();
    }
}