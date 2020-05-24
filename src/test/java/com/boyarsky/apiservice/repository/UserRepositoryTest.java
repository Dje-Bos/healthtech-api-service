package com.boyarsky.apiservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository testedInstance;

    @Test
    void getUserByEmail() {
    }

    @Test
    void removeUserByEmail() {
    }

    @Test
    void getUserById() {
    }
}