package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.config.AppProperties;
import com.boyarsky.apiservice.dto.ProtocolValidationResult;
import com.boyarsky.apiservice.dto.RecommendationDto;
import com.boyarsky.apiservice.entity.measurement.Measurement;
import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.service.RecommendationService;
import com.boyarsky.apiservice.service.impl.exception.RecommendationServiceException;
import com.boyarsky.apiservice.util.MeasurementUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.boyarsky.apiservice.mapper.RecommendationMapper.RECOMMENDATION_MAPPER;
import static java.lang.String.format;

@Service
public class ProtocolBasedRecommendationService implements RecommendationService {
    private final String recommendationServiceUrl;
    private final RestTemplate restTemplate;
    private final MeasurementRepository repository;

    public ProtocolBasedRecommendationService(RestTemplate restTemplate, AppProperties props, MeasurementRepository repository) {
        this.restTemplate = restTemplate;
        this.recommendationServiceUrl = props.getRecommendationServiceUrl();
        this.repository = repository;
    }

    @Override
    public List<RecommendationDto> findRecommendationsForUser(Long userId, ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        List<Measurement> userMeasurements = repository.findByUserIdAndCreatedTimeIsBetweenOrderByCreatedTimeDesc(userId, startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime());
        ResponseEntity<ProtocolValidationResult[]> protocolValidationResponse = restTemplate.postForEntity(recommendationServiceUrl + "/v1/protocols", userMeasurements.stream().map(MeasurementUtil::toDto).collect(Collectors.toList()), ProtocolValidationResult[].class);
        if (protocolValidationResponse.getStatusCode().is2xxSuccessful()) {
            return Arrays.stream(protocolValidationResponse.getBody())
                    .map(RECOMMENDATION_MAPPER::fromProtocolValidationResult)
                    .collect(Collectors.toList());
        } else {
            throw new RecommendationServiceException(format("Failed to retrieve recommendations using service: responded with '%d' status code", protocolValidationResponse.getStatusCodeValue()));
        }
    }
}
