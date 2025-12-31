package com.example.coldchain.web;

import com.example.coldchain.domain.AuditLog;
import com.example.coldchain.repo.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditlogs")
@RequiredArgsConstructor
public class AuditLogController {
  private final AuditLogRepository repo;

  @GetMapping
  public List<AuditLog> list() {
    return repo.findAll();
  }
}
