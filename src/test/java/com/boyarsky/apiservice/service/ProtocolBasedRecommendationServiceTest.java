package com.boyarsky.apiservice.service;

import com.boyarsky.apiservice.config.AppProperties;
import com.boyarsky.apiservice.repository.MeasurementRepository;
import com.boyarsky.apiservice.service.impl.ProtocolBasedRecommendationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProtocolBasedRecommendationServiceTest {
    @InjectMocks
    private ProtocolBasedRecommendationService testedInstance;

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private MeasurementRepository repository;
    @Mock
    private AppProperties appProperties;

    @Test
    void findRecommendationsForUser() {
    }
}