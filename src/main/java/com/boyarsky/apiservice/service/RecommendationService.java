package com.boyarsky.apiservice.service;

import java.time.ZonedDateTime;
import java.util.List;

public interface RecommendationService {
    List<?> findRecommendationsForUser(Long userId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);
}
