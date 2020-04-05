package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreateGlucoseDto;
import com.boyarsky.apiservice.entity.measurement.GlucoseMeasurement;
import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.service.GlucoseService;
import org.springframework.stereotype.Service;

import static com.boyarsky.apiservice.mapper.GlucoseMeasurementMapper.GLUCOSE_MAPPER;

@Service
public class GlucoseServiceImpl implements GlucoseService {
    private MeasurementRepository measurementRepository;
    private UserRepository userRepository;

    public GlucoseServiceImpl(MeasurementRepository measurementRepository, UserRepository userRepository) {
        this.measurementRepository = measurementRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MeasurementDto createForUser(CreateGlucoseDto newGlucose, Long userId) {
        GlucoseMeasurement newGlucoseEntity = GLUCOSE_MAPPER.create(newGlucose);
        newGlucoseEntity.setUser(userRepository.getUserById(userId));
        return GLUCOSE_MAPPER.toDto(measurementRepository.save(newGlucoseEntity));
    }
}
