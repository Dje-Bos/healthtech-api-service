package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.dto.CreateMeasurementRequest;
import com.boyarsky.apiservice.dto.MeasurementDto;
import com.boyarsky.apiservice.security.UserPrincipal;
import com.boyarsky.apiservice.service.MeasurementService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1 + "/measurement-entries")
@Api("Measurement API")
public class MeasurementController {

    private MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("/{uid}")
    @ApiOperation(value = "Sample test endpoint", authorizations = @Authorization(value = "JWT"))
    public ResponseEntity<?> measure(@PathVariable("uid") String uid) {
        return ResponseEntity.ok(uid);
    }

    @GetMapping(params = {"page"})
    @ApiOperation(value = "Get measurement", authorizations = @Authorization(value = "JWT"))
    public ResponseEntity<List<MeasurementDto>> get(@ApiIgnore @AuthenticationPrincipal UserPrincipal user,  @ApiParam(name = "page", required = true) @RequestParam("page") Integer page) {
        List<MeasurementDto> foundMeasurements = measurementService.getForUser(user.getId(), page);
        if (foundMeasurements.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundMeasurements);
    }

    @PostMapping
    @ApiOperation(value = "Create new measurement", authorizations = @Authorization(value = "JWT"))
    public ResponseEntity<MeasurementDto> create(@ApiIgnore @AuthenticationPrincipal UserPrincipal user, @RequestBody CreateMeasurementRequest measurementRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(measurementService.create(user.getId(), measurementRequest));
    }
}
