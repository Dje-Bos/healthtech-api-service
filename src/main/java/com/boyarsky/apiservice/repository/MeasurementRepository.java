package com.boyarsky.apiservice.repository;

import com.boyarsky.apiservice.entity.MeasurementEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeasurementRepository extends CrudRepository<MeasurementEntry, UUID> {

    List<MeasurementEntry> findByUserId(Long userId, Pageable pageable);
}
