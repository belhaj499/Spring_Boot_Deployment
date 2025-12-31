package com.example.coldchain.web;

import com.example.coldchain.domain.Ticket;
import com.example.coldchain.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
  private final TicketService service;

  @GetMapping
  public List<Ticket> list() {
    return service.list();
  }

  @PostMapping
  public Ticket create(@RequestBody Map<String, String> body) {
    return service.create(body.getOrDefault("description", ""));
  }

  @PutMapping("/<built-in function id>/close")
  public Ticket close(@PathVariable Long id) {
    return service.close(id);
  }
}
