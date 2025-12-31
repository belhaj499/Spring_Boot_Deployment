package com.example.coldchain.service;

import com.example.coldchain.domain.Ticket;
import com.example.coldchain.domain.TicketStatus;
import com.example.coldchain.repo.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
  private final TicketRepository repo;
  private final AuditLogService audit;

  public Ticket create(String description) {
    Ticket t = repo.save(Ticket.builder()
        .description(description == null ? "" : description)
        .status(TicketStatus.OPEN)
        .createdAt(Instant.now())
        .build());
    audit.log("TICKET_CREATED id=" + t.getId());
    return t;
  }

  public List<Ticket> list() {
    return repo.findAll();
  }

  public Ticket close(Long id) {
    Ticket t = repo.findById(id).orElseThrow();
    t.setStatus(TicketStatus.CLOSED);
    Ticket saved = repo.save(t);
    audit.log("TICKET_CLOSED id=" + id);
    return saved;
  }
}
