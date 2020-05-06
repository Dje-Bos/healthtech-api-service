package com.boyarsky.apiservice.repository;

import com.boyarsky.apiservice.entity.measurement.Measurement;
import com.boyarsky.apiservice.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    List<Measurement> findByUserIdOrderByCreatedTimeDesc(Long userId, Pageable pageable);

    List<Measurement> findByUserIdAndCreatedTimeIsBetween(Long userId, LocalDateTime start, LocalDateTime end);

    void removeAllByUser(User user);
}
