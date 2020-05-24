package com.boyarsky.apiservice.service.impl;

import com.boyarsky.apiservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GoogleReminderServiceImplTest {

    @InjectMocks
    private GoogleReminderServiceImpl testedInstance;

    @Mock
    private OAuth2AuthorizedClientService auth2AuthorizedClientService;
    @Mock
    private UserRepository userRepository;

    @Test
    void create() {
    }

    @Test
    void get() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAllWithinTimeSlot() {
    }
}