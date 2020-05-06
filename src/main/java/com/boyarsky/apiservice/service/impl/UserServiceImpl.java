package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.SignUpRequestDto;
import com.boyarsky.apiservice.dto.UserDto;
import com.boyarsky.apiservice.entity.user.AuthType;
import com.boyarsky.apiservice.entity.user.User;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.service.MeasurementService;
import com.boyarsky.apiservice.service.RoleService;
import com.boyarsky.apiservice.service.UserService;
import com.boyarsky.apiservice.service.impl.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static com.boyarsky.apiservice.mapper.UserMapper.USER_MAPPER;
import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final MeasurementService measurementService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, MeasurementService measurementService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.measurementService = measurementService;
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

    @Override
    @Transactional
    public void removeUserAccount(Long userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, format("User not found: uid=%s", userId));
        }
        measurementService.removeAllByUser(user);
        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.getUserById(userId);
    }
}
