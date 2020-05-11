package com.boyarsky.apiservice.repository;

import com.boyarsky.apiservice.entity.measurement.Measurement;
import com.boyarsky.apiservice.entity.measurement.MeasurementType;
import com.boyarsky.apiservice.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    List<Measurement> findByUserIdAndCreatedTimeIsBetweenOrderByCreatedTimeAsc(Long userId, LocalDateTime start, LocalDateTime end);

    void removeAllByUser(User user);

    @Query(value = "SELECT m from Measurement m WHERE m.user.id = :userId AND TYPE(m) IN :types ORDER BY m.createdTime desc")
    List<Measurement> findByTypeAndUserOrderByTimeDesc(Long userId, List<Class<? extends Measurement>> types, Pageable pageable);

    List<Measurement> findAllByUser(User user);
}
