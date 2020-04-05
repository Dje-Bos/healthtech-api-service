package com.boyarsky.apiservice.mapper;

import com.boyarsky.apiservice.dto.RoleDto;
import com.boyarsky.apiservice.entity.user.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper ROLE_MAPPER = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "name", source = "uid")
    RoleDto toDto(Role role);
}
