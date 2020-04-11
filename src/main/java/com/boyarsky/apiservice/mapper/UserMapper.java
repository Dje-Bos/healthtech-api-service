package com.boyarsky.apiservice.mapper;

import com.boyarsky.apiservice.dto.SignUpRequestDto;
import com.boyarsky.apiservice.dto.UserDto;
import com.boyarsky.apiservice.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = RoleMapper.class)
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    @Mappings({
            @Mapping(target = "name"),
            @Mapping(target = "email"),
            @Mapping(target = "password"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "auth", ignore = true),
            @Mapping(target = "creationTime", ignore = true),
            @Mapping(target = "isActive", ignore = true),
            @Mapping(target = "pictureUrl", ignore = true),
            @Mapping(target = "roles", ignore = true),
    })
    User fromSignUpRequest(SignUpRequestDto signUpRequest);
}
