package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreatePulseDto;
import com.boyarsky.apiservice.entity.measurement.PulseMeasurement;
import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.service.PulseService;
import org.springframework.stereotype.Service;

import static com.boyarsky.apiservice.mapper.PulseMeasurementMapper.PULSE_MAPPER;

@Service
public class PulseServiceImpl implements PulseService {
    private MeasurementRepository measurementRepository;
    private UserRepository userRepository;

    public PulseServiceImpl(MeasurementRepository measurementRepository, UserRepository userRepository) {
        this.measurementRepository = measurementRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MeasurementDto createForUser(CreatePulseDto newPulse, Long userId) {
        PulseMeasurement newPulseEntity = PULSE_MAPPER.create(newPulse);
        newPulseEntity.setUser(userRepository.getUserById(userId));
        return PULSE_MAPPER.toDto(measurementRepository.save(newPulseEntity));
    }
}
