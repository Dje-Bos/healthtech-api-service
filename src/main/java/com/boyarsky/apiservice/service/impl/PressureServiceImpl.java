package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreatePressureDto;
import com.boyarsky.apiservice.entity.measurement.PressureMeasurement;
import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.service.PressureService;
import org.springframework.stereotype.Service;

import static com.boyarsky.apiservice.mapper.PressureMeasurementMapper.PRESSURE_MAPPER;

@Service
public class PressureServiceImpl implements PressureService {
    private MeasurementRepository measurementRepository;
    private UserRepository userRepository;

    public PressureServiceImpl(MeasurementRepository measurementRepository, UserRepository userRepository) {
        this.measurementRepository = measurementRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MeasurementDto createForUser(CreatePressureDto newPressure, Long userId) {
        PressureMeasurement newPressureEntity = PRESSURE_MAPPER.create(newPressure);
        newPressureEntity.setUser(userRepository.getUserById(userId));
        return PRESSURE_MAPPER.toDto(measurementRepository.save(newPressureEntity));
    }
}
