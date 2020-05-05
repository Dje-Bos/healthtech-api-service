package com.boyarsky.apiservice.dto;

import lombok.Data;

@Data
public class RecommendationDto {
    private SeverityLevel severityLevel;
    private String description;
    private String protocol;
    private String reminder;
}
