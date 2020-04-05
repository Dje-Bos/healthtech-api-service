package com.boyarsky.apiservice.mapper;

import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.dto.measurement.CreateTempDto;
import com.boyarsky.apiservice.entity.measurement.TempMeasurement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(config = MeasurementConfig.class)
public interface TempMeasurementMapper {
    TempMeasurementMapper TEMP_MAPPER = Mappers.getMapper(TempMeasurementMapper.class);

    @Mappings({
            @Mapping(source = "temp", target = "value"),
            @Mapping(target = "type", expression = "java(com.boyarsky.apiservice.entity.measurement.MeasurementType.TEMPERATURE)")
    })
    MeasurementDto toDto(TempMeasurement entity);

    @Mappings({
            @Mapping(target = "temp"),
            @Mapping(target = "uid", ignore = true),
            @Mapping(target = "createdTime", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    TempMeasurement create(CreateTempDto newTemp);
}
