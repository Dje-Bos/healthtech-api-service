package com.boyarsky.apiservice.controller.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(useDefaultFilters = false)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUserByEmail() throws Exception {
        mockMvc.perform(get("/v1/user/some@example.com"))
                .andDo(print());
    }

    @Test
    void getRoleByUid() throws Exception {
        mockMvc.perform(get("/v1/user/role/USER"))
                .andDo(print());
    }

    @Test
    void getCurrentUser() throws Exception {
        mockMvc.perform(get("/v1/user/me"))
                .andDo(print());
    }

    @Test
    void updateCurrentUser() throws Exception {
        mockMvc.perform(put("/v1/user"))
                .andDo(print());
    }

    @Test
    void removeUserAccount() throws Exception {
        mockMvc.perform(post("/v1/user/some@example.com"))
                .andDo(print());
    }
}