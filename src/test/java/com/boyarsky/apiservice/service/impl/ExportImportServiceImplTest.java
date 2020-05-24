package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExportImportServiceImplTest {

    @InjectMocks
    private ExportImportServiceImpl testedInstance;
    @Mock
    private UserService userService;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private MeasurementRepository measurementRepository;

    @Test
    void exportAllMeasurements() {
    }

    @Test
    void importMeasurements() {
    }
}