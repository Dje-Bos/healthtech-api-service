package com.boyarsky.apiservice.controller.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(useDefaultFilters = false)
class PulseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createPulse() throws Exception {
        mockMvc.perform(post(API_VERSION_1 + "/measurements/pulse")).andDo(print());
    }
}