package com.boyarsky.apiservice.controller.v1;

import com.boyarsky.apiservice.service.UserService;
import com.boyarsky.apiservice.service.impl.TokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(useDefaultFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private TokenProvider tokenProvider;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserService userService;

    @Test
    void shouldLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/login").content("{}")).andDo(print());
    }

    @Test
    void shouldSignUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/signup").content("{}")).andDo(print());
    }
}