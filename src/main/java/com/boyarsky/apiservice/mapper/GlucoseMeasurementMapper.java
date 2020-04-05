package com.boyarsky.apiservice.mapper;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreateGlucoseDto;
import com.boyarsky.apiservice.entity.measurement.GlucoseMeasurement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(config = MeasurementConfig.class)
public interface GlucoseMeasurementMapper {
    GlucoseMeasurementMapper GLUCOSE_MAPPER = Mappers.getMapper(GlucoseMeasurementMapper.class);

    @Mappings({
            @Mapping(source = "glucose", target = "value"),
            @Mapping(target = "type", expression = "java(com.boyarsky.apiservice.entity.measurement.MeasurementType.GLUCOSE)")
    })
    MeasurementDto toDto(GlucoseMeasurement entity);

    @Mappings({
            @Mapping(target = "glucose"),
            @Mapping(target = "uid", ignore = true),
            @Mapping(target = "createdTime", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    GlucoseMeasurement create(CreateGlucoseDto newGlucose);
}
