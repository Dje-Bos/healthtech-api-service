package com.boyarsky.apiservice.controller.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(useDefaultFilters = false)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void getAllRecommendationsForPeriod() throws Exception {
        mockMvc.perform(get("/v1/recommendations?startDate=1&endDate=4"))
                .andDo(print());
    }
}