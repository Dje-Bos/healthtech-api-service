package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.CreateMeasurementRequest;
import com.boyarsky.apiservice.dto.MeasurementDto;
import com.boyarsky.apiservice.security.UserPrincipal;
import com.boyarsky.apiservice.service.MeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1 + "/measurement-entries")
public class MeasurementController {

    private MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("/{uid}")
    @Operation(description = "Sample test endpoint", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<?> measure(@PathVariable("uid") String uid) {
        return ResponseEntity.ok(uid);
    }

    @GetMapping(params = {"page"})
    @Operation(description = "Get measurement", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<List<MeasurementDto>> get(@AuthenticationPrincipal UserPrincipal user,  @RequestParam("page") Integer page) {
        List<MeasurementDto> foundMeasurements = measurementService.getForUser(user.getId(), page);
        if (foundMeasurements.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundMeasurements);
    }

    @PostMapping
    @Operation(description = "Create new measurement", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<MeasurementDto> create(@AuthenticationPrincipal UserPrincipal user, @RequestBody CreateMeasurementRequest measurementRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(measurementService.create(user.getId(), measurementRequest));
    }
}
