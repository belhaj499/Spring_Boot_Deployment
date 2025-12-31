package com.example.coldchain.service;

import com.example.coldchain.domain.Measurement;
import com.example.coldchain.repo.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasurementService {

  private final MeasurementRepository repo;
  private final TicketService tickets;
  private final AuditLogService audit;

  @Value("${app.threshold.min}")
  private double minT;

  @Value("${app.threshold.max}")
  private double maxT;

  public double getMinT() {
    return minT;
  }

  public double getMaxT() {
    return maxT;
  }

  public Measurement save(Measurement m) {
    if (m.getTimestamp() == null) {
      m.setTimestamp(Instant.now());
    }

    Measurement saved = repo.save(m);
    audit.log("MEASUREMENT_SAVED id=" + saved.getId());

    return saved;
  }

  public List<Measurement> list() {
    return repo.findAll();
  }

  public Measurement latest() {
    return repo.findTopByOrderByTimestampDesc().orElse(null);
  }
}
