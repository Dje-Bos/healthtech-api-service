package com.boyarsky.apiservice.controller.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(useDefaultFilters = false)
class ImportExportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void exportAllMeasurements() throws Exception {
        mockMvc.perform(post("/v1/export")).andDo(print());
    }

    @Test
    void importMeasurements() throws Exception {
        mockMvc.perform(post("/v1/import")).andDo(print());
    }
}