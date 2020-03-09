package com.boyarsky.apiservice.mapper;

import com.boyarsky.apiservice.dto.MeasurementDto;
import com.boyarsky.apiservice.entity.MeasurementEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MeasurementMapper {
    MeasurementMapper MEASUREMENT_MAPPER = Mappers.getMapper( MeasurementMapper.class );

    @Mapping(target = "type", source = "measurementType")
    MeasurementDto toDto(MeasurementEntry entity);
}
