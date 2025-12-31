package com.example.coldchain.auth;

import com.example.coldchain.service.AuditLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService auth;
  private final AuditLogService audit;

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
    return auth.login(req.getUsername(), req.getPassword())
        .map(res -> {
          audit.log("LOGIN: " + res.getUsername());
          return ResponseEntity.ok(res);
        })
            .orElse(ResponseEntity.status(401).build());

  }
}
