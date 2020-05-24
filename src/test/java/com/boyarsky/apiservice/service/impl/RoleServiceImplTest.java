package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @InjectMocks
    private RoleServiceImpl testedInstance;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void createOrGetByUid() {
    }
}