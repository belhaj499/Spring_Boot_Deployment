package com.example.coldchain.web;

import com.example.coldchain.domain.Measurement;
import com.example.coldchain.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/measurements")
@RequiredArgsConstructor
public class MeasurementController {
  private final MeasurementService service;

  @PostMapping
  public Measurement create(@RequestBody Measurement m) {
    return service.save(m);
  }

  @GetMapping
  public List<Measurement> list() {
    return service.list();
  }

  @GetMapping("/latest")
  public Measurement latest() {
    return service.latest();
  }

  @GetMapping("/export/csv")
  public ResponseEntity<byte[]> exportCsv() {
    List<Measurement> list = service.list();
    StringBuilder sb = new StringBuilder();
    sb.append("id,temperature,humidity,timestamp\n");
    for (Measurement m : list) {
      sb.append(m.getId()).append(",")
        .append(m.getTemperature()).append(",")
        .append(m.getHumidity()).append(",")
        .append(m.getTimestamp() == null ? "" : DateTimeFormatter.ISO_INSTANT.format(m.getTimestamp()))
        .append("\n");
    }
    byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=measurements.csv")
        .contentType(new MediaType("text", "csv"))
        .body(bytes);
  }
}
