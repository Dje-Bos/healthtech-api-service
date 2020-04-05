package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.measurement.CreatePressureDto;
import com.boyarsky.apiservice.security.UserPrincipal;
import com.boyarsky.apiservice.service.PressureService;
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
@RequestMapping(API_VERSION_1 + "/measurements/pressure")
public class PressureController {
    private PressureService pressureService;

    public PressureController(PressureService pressureService) {
        this.pressureService = pressureService;
    }

    @PostMapping
    @Operation(description = "Create new pressure measurement", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<?> createPressure(@AuthenticationPrincipal UserPrincipal user, @Valid @RequestBody CreatePressureDto createPressureDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pressureService.createForUser(createPressureDto, user.getId()));
    }
}
