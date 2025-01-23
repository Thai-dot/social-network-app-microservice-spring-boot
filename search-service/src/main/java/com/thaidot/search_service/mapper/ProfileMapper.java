package com.thaidot.search_service.mapper;

import com.thaidot.event.dto.ProfileEvent;
import com.thaidot.search_service.dto.request.ProfileRequest;
import com.thaidot.search_service.dto.response.ProfileResponse;
import com.thaidot.search_service.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileResponse toProfileResponse(Profile profile);

    Profile toProfile(ProfileRequest profile);
    ProfileRequest toProfileRequestFromEvent(ProfileEvent profile);
}
