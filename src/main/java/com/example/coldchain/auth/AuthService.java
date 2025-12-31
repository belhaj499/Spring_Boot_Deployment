package com.example.coldchain.auth;

import com.example.coldchain.domain.User;
import com.example.coldchain.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository users;

  // token -> username (simple, no JWT to avoid over-engineering)
  private final Map<String, String> tokens = new ConcurrentHashMap<>();

  public String hash(String raw) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for (byte b : digest) sb.append(String.format("%02x", b));
      return sb.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Optional<LoginResponse> login(String username, String password) {
    return users.findByUsername(username)
        .filter(u -> u.getPasswordHash().equals(hash(password)))
        .map(u -> {
          String token = UUID.randomUUID().toString();
          tokens.put(token, u.getUsername());
          return LoginResponse.builder()
              .token(token)
              .username(u.getUsername())
              .role(u.getRole())
              .build();
        });
  }

  public Optional<User> userFromToken(String token) {
    if (token == null || token.isBlank()) return Optional.empty();
    String username = tokens.get(token);
    if (username == null) return Optional.empty();
    return users.findByUsername(username);
  }
}
