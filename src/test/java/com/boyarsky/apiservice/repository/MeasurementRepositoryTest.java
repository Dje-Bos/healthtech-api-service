package com.boyarsky.apiservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MeasurementRepositoryTest {

    @Autowired
    private MeasurementRepository testedInstance;

    @Test
    void findByUserIdAndCreatedTimeIsBetweenOrderByCreatedTimeAsc() {
    }

    @Test
    void removeAllByUser() {
    }

    @Test
    void findByTypeAndUserOrderByTimeDesc() {
    }

    @Test
    void findAllByUser() {
    }
}