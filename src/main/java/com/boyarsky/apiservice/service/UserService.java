package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.SignUpRequestDto;
import com.boyarsky.apiservice.dto.UserDto;

public interface UserService {
    UserDto register(SignUpRequestDto signUpRequestDto);
}
