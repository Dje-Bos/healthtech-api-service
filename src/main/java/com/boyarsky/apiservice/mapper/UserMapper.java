package com.boyarsky.apiservice.mapper;

import com.boyarsky.apiservice.dto.UserDto;
import com.boyarsky.apiservice.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = RoleMapper.class)
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
}
