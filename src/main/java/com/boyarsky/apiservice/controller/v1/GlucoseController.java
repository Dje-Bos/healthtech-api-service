package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.measurement.CreateGlucoseDto;
import com.boyarsky.apiservice.security.UserPrincipal;
import com.boyarsky.apiservice.service.GlucoseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class GlucoseController {
    private GlucoseService glucoseService;

    public GlucoseController(GlucoseService glucoseService) {
        this.glucoseService = glucoseService;
    }

    @PostMapping
    @Operation(description = "Create new glucose measurement", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<?> createGlucose(@AuthenticationPrincipal UserPrincipal user, @Valid @RequestBody CreateGlucoseDto createGlucoseRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(glucoseService.createForUser(createGlucoseRequest, user.getId()));
    }
}
