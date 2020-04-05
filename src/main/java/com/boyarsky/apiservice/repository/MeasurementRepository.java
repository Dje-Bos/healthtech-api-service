package com.boyarsky.apiservice.repository;

import com.boyarsky.apiservice.entity.measurement.Measurement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeasurementRepository extends CrudRepository<Measurement, UUID> {

    List<Measurement> findByUserIdOrderByCreatedTimeDesc(Long userId, Pageable pageable);
}
