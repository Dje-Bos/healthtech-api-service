package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.measurement.CreateTempDto;
import com.boyarsky.apiservice.dto.measurement.CreateWeightDto;
import com.boyarsky.apiservice.security.UserPrincipal;
import com.boyarsky.apiservice.service.WeightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1 + "/measurements/weight")
public class WeightController {
    private WeightService weightService;

    public WeightController(WeightService weightService) {
        this.weightService = weightService;
    }

    @PostMapping
    @Operation(description = "Create new weight measurement", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<?> createWeight(@AuthenticationPrincipal UserPrincipal user, @Valid @RequestBody CreateWeightDto createWeightDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(weightService.createForUser(createWeightDto, user.getId()));
    }

}
