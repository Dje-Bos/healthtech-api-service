package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.SignUpRequestDto;
import com.boyarsky.apiservice.dto.UserDto;
import com.boyarsky.apiservice.entity.user.AuthType;
import com.boyarsky.apiservice.entity.user.User;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.service.RoleService;
import com.boyarsky.apiservice.service.UserService;
import com.boyarsky.apiservice.service.impl.exception.UserAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.boyarsky.apiservice.mapper.UserMapper.USER_MAPPER;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public UserDto register(SignUpRequestDto signUpRequestDto) {
        if(userRepository.getUserByEmail(signUpRequestDto.getEmail()) != null) {
            throw new UserAlreadyExistsException("Email address already in use.");
        }

        User user = USER_MAPPER.fromSignUpRequest(signUpRequestDto);

        user.setAuth(AuthType.BASIC);
        user.setRoles(Set.of(roleService.createOrGetByUid("USER")));

        return USER_MAPPER.toDto(userRepository.save(user));
    }
}
