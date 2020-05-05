package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.ApiErrorDto;
import com.boyarsky.apiservice.security.UserPrincipal;
import com.boyarsky.apiservice.service.RecommendationService;
import com.boyarsky.apiservice.service.impl.exception.RecommendationServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1 + "/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    @Operation(description = "Get all recommendations for user using measurements in range between startDate and endDate", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<?> getAllRecommendationsForPeriod(@AuthenticationPrincipal UserPrincipal user,
                                                            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDateTime,
                                                            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDateTime) {
        return ResponseEntity.ok(recommendationService.findRecommendationsForUser(user.getId(), startDateTime, endDateTime));
    }

    @ExceptionHandler(RecommendationServiceException.class)
    public ResponseEntity<ApiErrorDto> error(RecommendationServiceException e) {
        ApiErrorDto errorDto = new ApiErrorDto();
        errorDto.setErr(e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorDto);
    }
}
