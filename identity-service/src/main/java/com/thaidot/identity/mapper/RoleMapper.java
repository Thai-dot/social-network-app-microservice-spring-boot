package com.thaidot.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.thaidot.identity.dto.request.RoleRequest;
import com.thaidot.identity.dto.response.RoleResponse;
import com.thaidot.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
