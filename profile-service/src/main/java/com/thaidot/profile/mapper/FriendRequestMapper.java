package com.thaidot.profile.mapper;

import com.thaidot.profile.dto.request.FollowRequest;
import com.thaidot.profile.dto.request.FriendRequestRequest;
import com.thaidot.profile.dto.response.FriendRequestResponse;
import com.thaidot.profile.dto.response.FriendRequestResponseWithUserProfile;
import com.thaidot.profile.entity.FriendRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {
    public FriendRequest toFriendRequest(FriendRequestRequest request);
    public FriendRequestResponse toFriendRequestResponse(FriendRequest friendRequest);

    public FriendRequestResponseWithUserProfile toFriendRequestResponseWithUserProfile(FriendRequest friendRequest);

    @Mapping(source = "followerID", target = "senderID")
    @Mapping(source = "followeeID", target = "receiverID")
    public FriendRequest toFriendRequestFromFollow(FollowRequest followRequest);
}
