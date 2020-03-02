package com.boyarsky.apiservice.controller.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.boyarsky.apiservice.controller.ApiConstants.API_VERSION_1;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(useDefaultFilters = false)
class MeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnUid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_VERSION_1 + "/measurement/123"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
