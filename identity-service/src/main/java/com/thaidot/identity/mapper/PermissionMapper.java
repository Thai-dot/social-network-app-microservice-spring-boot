package com.thaidot.identity.mapper;

import org.mapstruct.Mapper;

import com.thaidot.identity.dto.request.PermissionRequest;
import com.thaidot.identity.dto.response.PermissionResponse;
import com.thaidot.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
