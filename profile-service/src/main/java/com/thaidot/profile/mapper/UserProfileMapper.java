package com.thaidot.profile.mapper;

import com.thaidot.event.dto.ProfileEvent;
import com.thaidot.profile.dto.request.UserProfileCreationRequest;
import com.thaidot.profile.dto.response.UserProfileCreationResponse;
import com.thaidot.profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileCreationRequest request);
    UserProfileCreationResponse toUserProfileResponse(UserProfile userProfile);
    ProfileEvent toProfileEvent(UserProfile userProfile);
}
