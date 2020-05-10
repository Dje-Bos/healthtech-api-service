package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.measurement.CreatePulseDto;
import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.security.UserPrincipal;
import com.boyarsky.apiservice.service.PulseService;
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
@RequestMapping(API_VERSION_1 + "/measurements/pulse")
public class PulseController {
    private PulseService pulseService;

    public PulseController(PulseService measurementService) {
        this.pulseService = measurementService;
    }

    @PostMapping
    @Operation(description = "Create new pulse measurement", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<MeasurementDto> createPulse(@AuthenticationPrincipal UserPrincipal user, @Valid @RequestBody CreatePulseDto createPulseDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pulseService.createForUser(createPulseDto, user.getId()));
    }

}
