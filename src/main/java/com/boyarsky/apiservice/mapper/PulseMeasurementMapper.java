package com.boyarsky.apiservice.mapper;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreatePulseDto;
import com.boyarsky.apiservice.entity.measurement.PulseMeasurement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(config = MeasurementConfig.class)
public interface PulseMeasurementMapper {
    PulseMeasurementMapper PULSE_MAPPER = Mappers.getMapper(PulseMeasurementMapper.class);
    @Mappings({
            @Mapping(source = "pulse", target = "value"),
            @Mapping(target = "type", expression = "java(com.boyarsky.apiservice.entity.measurement.MeasurementType.PULSE)")
    })
    MeasurementDto toDto(PulseMeasurement entity);

    @Mappings({
            @Mapping(target = "pulse"),
            @Mapping(target = "uid", ignore = true),
            @Mapping(target = "createdTime", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    PulseMeasurement create(CreatePulseDto newPulse);
}
