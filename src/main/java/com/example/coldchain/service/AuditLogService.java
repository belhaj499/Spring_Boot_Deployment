package com.example.coldchain.service;

import com.example.coldchain.domain.AuditLog;
import com.example.coldchain.repo.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuditLogService {
  private final AuditLogRepository repo;

  public void log(String action) {
    repo.save(AuditLog.builder()
        .action(action)
        .date(Instant.now())
        .build());
  }
}
