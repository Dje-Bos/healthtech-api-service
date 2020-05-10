package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.controller.v1.dto.MeasurementsByDate;
import com.boyarsky.apiservice.dto.measurement.MeasurementDto;
import com.boyarsky.apiservice.entity.measurement.MeasurementType;
import com.boyarsky.apiservice.security.UserPrincipal;
import com.boyarsky.apiservice.service.MeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1 + "/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("/{uid}")
    @Operation(description = "Sample test endpoint", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<?> measure(@PathVariable("uid") String uid) {
        return ResponseEntity.ok(uid);
    }

    @GetMapping(params = {"page", "types"})
    @Operation(description = "Get measurements grouped by date of selected type", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<List<MeasurementsByDate>> get(@AuthenticationPrincipal UserPrincipal user, @RequestParam("page") Integer page, @RequestParam("types") MeasurementType[] types) {
        var foundMeasurements = measurementService.getGroupedByDateOfType(user.getId(), types, page);
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

    @GetMapping
    @Operation(description = "Get measurements in time range", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<List<MeasurementDto>> getInTimeRange(@AuthenticationPrincipal UserPrincipal user,
                                                               @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDate,
                                                               @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDate) {
        var foundMeasurements = measurementService.getInTimeRange(user.getId(), startDate.toLocalDateTime(), endDate.toLocalDateTime());
        if (foundMeasurements.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundMeasurements);
    }
}
