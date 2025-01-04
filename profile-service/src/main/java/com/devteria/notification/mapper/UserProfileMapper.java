package com.devteria.notification.mapper;

import com.devteria.notification.dto.request.UserProfileCreationRequest;
import com.devteria.notification.dto.response.UserProfileCreationResponse;
import com.devteria.notification.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileCreationRequest request);
    UserProfileCreationResponse toUserProfileResponse(UserProfile userProfile);
}
