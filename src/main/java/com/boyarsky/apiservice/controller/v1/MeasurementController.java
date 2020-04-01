package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.controller.v1.dto.MeasurementsByDate;
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

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Operation(description = "Get measurements grouped by date", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<List<MeasurementsByDate>> get(@AuthenticationPrincipal UserPrincipal user, @RequestParam("page") Integer page) {
        var foundMeasurements = measurementService.getGroupedByDate(user.getId(), page);
        if (foundMeasurements.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToMeasurementsByDate(foundMeasurements));
    }

    private List<MeasurementsByDate> convertToMeasurementsByDate(Map<LocalDate, List<MeasurementDto>> foundMeasurements) {
        return foundMeasurements.entrySet()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private MeasurementsByDate convert(Map.Entry<LocalDate, List<MeasurementDto>> entry) {
        var measurementsByDate = new MeasurementsByDate();
        measurementsByDate.setDate(entry.getKey());
        measurementsByDate.setMeasurements(entry.getValue());
        return measurementsByDate;
    }

    @PostMapping
    @Operation(description = "Create new measurement", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<?> create(@AuthenticationPrincipal UserPrincipal user, @Valid @RequestBody CreateMeasurementRequest measurementRequest) {
        if (!isValidMeasurementValue(measurementRequest)) {
            return ResponseEntity.badRequest().build();

        }
        return ResponseEntity.status(HttpStatus.CREATED).body(measurementService.create(user.getId(), measurementRequest));
    }

    private boolean isValidMeasurementValue(CreateMeasurementRequest measurementRequest) {
        boolean isValid;
        switch (measurementRequest.getType()) {
            case PULSE: {
                isValid = measurementRequest.getValue().matches("\\d{2,3}");
                break;
            }
            case PRESSURE: {
                isValid = measurementRequest.getValue().matches("\\d{2,3}/\\d{2,3}");
                break;
            }
            case WEIGHT: {
                isValid = measurementRequest.getValue().matches("\\d{2,3}\\.?(?<=\\.)\\d{0,2}");
                break;
            }
            case GLUCOSE: {
                isValid = measurementRequest.getValue().matches("\\d{1,3}\\.\\d{0,2}");
                break;
            }
            case TEMPERATURE: {
                isValid = measurementRequest.getValue().matches("\\d{1,3}\\.\\d?");
                break;
            }
            default: {
                isValid = false;
            }
        }
        return isValid;
    }
}
