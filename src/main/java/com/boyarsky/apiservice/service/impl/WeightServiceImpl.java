package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreateWeightDto;
import com.boyarsky.apiservice.entity.measurement.WeightMeasurement;
import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.repository.UserRepository;
import com.boyarsky.apiservice.service.WeightService;
import org.springframework.stereotype.Service;

import static com.boyarsky.apiservice.mapper.WeightMeasurementMapper.WEIGHT_MAPPER;

@Service
public class WeightServiceImpl implements WeightService {
    private MeasurementRepository measurementRepository;
    private UserRepository userRepository;

    public WeightServiceImpl(MeasurementRepository measurementRepository, UserRepository userRepository) {
        this.measurementRepository = measurementRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MeasurementDto createForUser(CreateWeightDto newWeight, Long userId) {
        WeightMeasurement newWeightEntity = WEIGHT_MAPPER.create(newWeight);
        newWeightEntity.setUser(userRepository.getUserById(userId));

        return WEIGHT_MAPPER.toDto(measurementRepository.save(newWeightEntity));
    }
}
