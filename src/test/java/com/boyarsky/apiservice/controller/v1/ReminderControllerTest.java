package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.service.GoogleReminderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(useDefaultFilters = false)
class ReminderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoogleReminderService reminderService;

    @Test
    void getAllReminders() throws Exception {
        mockMvc.perform(get("/v1/reminders?startDate=1&endDate=4"))
                .andDo(print());
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/v1/reminders"))
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/reminders/123"))
                .andDo(print());
    }

    @Test
    void shouldGet() throws Exception {
        mockMvc.perform(get("/v1/reminders/123"))
                .andDo(print());
    }
}