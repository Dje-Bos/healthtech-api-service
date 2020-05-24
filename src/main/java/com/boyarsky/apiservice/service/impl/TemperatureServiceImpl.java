package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreateTempDto;
import com.boyarsky.apiservice.entity.measurement.TempMeasurement;
import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.service.TemperatureService;
import org.springframework.stereotype.Service;

import static com.boyarsky.apiservice.mapper.TempMeasurementMapper.TEMP_MAPPER;

@Service
public class TemperatureServiceImpl implements TemperatureService {
    private final MeasurementRepository measurementRepository;
    private final UserRepository userRepository;

    public TemperatureServiceImpl(MeasurementRepository measurementRepository, UserRepository userRepository) {
        this.measurementRepository = measurementRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MeasurementDto createForUser(CreateTempDto newTemp, Long userId) {
        TempMeasurement newTempEntity = TEMP_MAPPER.create(newTemp);
        newTempEntity.setUser(userRepository.getUserById(userId));
        return TEMP_MAPPER.toDto(measurementRepository.save(newTempEntity));
    }
}
