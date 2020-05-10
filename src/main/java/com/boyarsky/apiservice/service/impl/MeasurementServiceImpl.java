package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.entity.measurement.GlucoseMeasurement;
import com.boyarsky.apiservice.entity.measurement.Measurement;
import com.boyarsky.apiservice.entity.measurement.MeasurementType;
import com.boyarsky.apiservice.entity.measurement.PressureMeasurement;
import com.boyarsky.apiservice.entity.measurement.PulseMeasurement;
import com.boyarsky.apiservice.entity.measurement.TempMeasurement;
import com.boyarsky.apiservice.entity.measurement.WeightMeasurement;
import com.boyarsky.apiservice.entity.user.User;
import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.service.MeasurementService;
import com.boyarsky.apiservice.util.MeasurementUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class MeasurementServiceImpl implements MeasurementService {
    private final MeasurementRepository measurementRepository;


    public MeasurementServiceImpl(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<LocalDate, List<MeasurementDto>> getGroupedByDateOfType(Long userId, MeasurementType[] types, int page) {
        List<Class<? extends Measurement>> measurementEntityClasses = Arrays.stream(types).map(this::toMeasurementEntityClass).collect(Collectors.toList());
        List<Measurement> foundMeasurements = measurementRepository.findByTypeAndUserOrderByTimeDesc(userId, measurementEntityClasses, PageRequest.of(page, 30));
        return foundMeasurements.stream()
                .map(MeasurementUtil::toDto)
                .collect(Collectors.groupingBy(measurementDto -> measurementDto.getCreated().toLocalDate(), LinkedHashMap::new, Collectors.toList()));
    }

    private Class<? extends Measurement> toMeasurementEntityClass(MeasurementType type) {
        switch (type) {
            case PULSE: {
                return PulseMeasurement.class;
            }
            case WEIGHT: {
                return WeightMeasurement.class;
            }
            case GLUCOSE: {
                return GlucoseMeasurement.class;
            }
            case PRESSURE: {
                return PressureMeasurement.class;
            }
            case TEMPERATURE: {
                return TempMeasurement.class;
            }
            default: {
                throw new IllegalArgumentException(format("Measurement type %s is not supported.", type));
            }
        }
    }

        @Override
    public List<MeasurementDto> getInTimeRange(Long userId, LocalDateTime start, LocalDateTime end) {
        return measurementRepository.findByUserIdAndCreatedTimeIsBetweenOrderByCreatedTimeDesc(userId, start, end).stream()
                .map(MeasurementUtil::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void removeAllByUser(User user) {
        measurementRepository.removeAllByUser(user);
    }

}
