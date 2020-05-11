package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.SignUpRequestDto;
import com.boyarsky.apiservice.dto.UserDto;
import com.boyarsky.apiservice.dto.UserUpdateDto;
import com.boyarsky.apiservice.entity.user.User;

public interface UserService {
    UserDto register(SignUpRequestDto signUpRequestDto);

    void removeUserAccount(Long userId);

    User getUserById(Long userId);

    UserDto update(Long userId, UserUpdateDto userUpdateDto);
}
