package com.thaidot.file_service.mapper;

import com.thaidot.file_service.dto.response.AvatarResponse;
import com.thaidot.file_service.entity.Avatar;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvatarMapper {
    AvatarResponse toAvatarResponse(Avatar avatar);
}
