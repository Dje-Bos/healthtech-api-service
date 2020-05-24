package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TemperatureServiceImplTest {
    @InjectMocks
    private TemperatureServiceImpl testedInstance;

    @Mock
    private MeasurementRepository measurementRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void createForUser() {
    }
}