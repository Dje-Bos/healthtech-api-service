package com.boyarsky.apiservice.mapper;

import com.boyarsky.apiservice.dto.ProtocolValidationResult;
import com.boyarsky.apiservice.dto.ProtocolValidationStatus;
import com.boyarsky.apiservice.dto.RecommendationDto;
import com.boyarsky.apiservice.dto.SeverityLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecommendationMapper {
    RecommendationMapper RECOMMENDATION_MAPPER = Mappers.getMapper(RecommendationMapper.class);

    @Mappings({
            @Mapping(target = "severityLevel", source = "status"),
            @Mapping(target = "description"),
            @Mapping(target = "protocol"),
            @Mapping(target = "reminder")
    })
    RecommendationDto fromProtocolValidationResult(ProtocolValidationResult res);

    @ValueMappings({
            @ValueMapping(source = "OK", target = "INFO"),
            @ValueMapping(source = "NORMAL", target = "INFO"),
            @ValueMapping(source = "CHECK_RISKS", target = "WARN"),
            @ValueMapping(source = "MEDICAL_HELP", target = "IMMEDIATE_REACTION")
    })
    SeverityLevel fromProtocolStatusToSeverityLevel(ProtocolValidationStatus status);
}
