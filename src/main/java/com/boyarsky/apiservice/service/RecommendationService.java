package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.dto.RecommendationDto;

import java.time.ZonedDateTime;
import java.util.List;

public interface RecommendationService {
    List<RecommendationDto> findRecommendationsForUser(Long userId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
}
