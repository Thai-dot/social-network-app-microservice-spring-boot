package com.thaidot.identity.mapper;

import com.thaidot.identity.dto.request.UserCreationRequest;
import com.thaidot.identity.dto.request.UserProfileCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    UserProfileCreationRequest toUserProfileCreationRequest(UserCreationRequest userProfile);
}
