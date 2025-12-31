package com.example.coldchain.repo;

import com.example.coldchain.domain.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
  Optional<Measurement> findTopByOrderByTimestampDesc();
}
