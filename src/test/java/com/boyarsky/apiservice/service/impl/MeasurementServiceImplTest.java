package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.repository.MeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceImplTest {
    @InjectMocks
    private MeasurementServiceImpl testedInstance;

    @Mock
    private MeasurementRepository measurementRepository;

    @Test
    void getGroupedByDateOfType() {
    }

    @Test
    void getInTimeRange() {
    }

    @Test
    void removeAllByUser() {
    }
}