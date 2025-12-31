package com.example.coldchain.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Measurement {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double temperature;
  private Double humidity;

  @Column(nullable = false)
  private Instant timestamp;

  @ManyToOne(optional = true)
  private Sensor sensor;
}
