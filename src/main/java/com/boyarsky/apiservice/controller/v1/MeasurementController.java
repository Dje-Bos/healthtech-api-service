package com.boyarsky.apiservice.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1 + "/measurement")
public class MeasurementController {

    @GetMapping("/{uid}")
    public ResponseEntity<?> measure(@PathVariable("uid") String uid) {
        return ResponseEntity.ok(uid);
    }
}
