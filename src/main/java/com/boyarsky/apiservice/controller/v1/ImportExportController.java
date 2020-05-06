package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.security.UserPrincipal;
import com.boyarsky.apiservice.service.ExportImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1)
public class ImportExportController {
    private final ExportImportService exportImportService;

    public ImportExportController(ExportImportService exportImportService) {
        this.exportImportService = exportImportService;
    }

    @GetMapping("/export")
    @Operation(description = "Export all user measurements", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<Resource> exportAllMeasurements(@AuthenticationPrincipal UserPrincipal user) throws IOException {
        Resource measurements = exportImportService.exportAllMeasurements(user.getId());
        return ResponseEntity.ok()
                .contentLength(measurements.contentLength())
                .contentType(MediaType.APPLICATION_JSON)
                .body(measurements);
    }

    @PostMapping(value = "/import", consumes = "multipart/form-data")
    @Operation(description = "Import user measurements", security = @SecurityRequirement(name = "JWT"))
    public ResponseEntity<?> importMeasurements(@AuthenticationPrincipal UserPrincipal user, @RequestParam("file") MultipartFile file) {
        exportImportService.importMeasurements(user.getId(), file);
        return ResponseEntity.ok().build();
    }
}
