package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.service.MeasurementService;
import com.boyarsky.apiservice.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl testedInstance;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private MeasurementService measurementService;

    @Test
    void register() {
    }

    @Test
    void removeUserAccount() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void update() {
    }
}