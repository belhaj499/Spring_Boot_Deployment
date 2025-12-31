package com.example.coldchain.auth;

import com.example.coldchain.domain.Role;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoginResponse {
  private String token;
  private String username;
  private Role role;
}
