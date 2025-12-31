package com.example.coldchain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AlertScheduler {

    private final MeasurementService measurementService;
    private final TelegramService telegram;
    private final EmailService email;

    private Instant lastAlertTime = null;

    @Scheduled(fixedRate = 300000) // vérifie toutes les 5 minutes
    public void checkTemperature() {

        var last = measurementService.latest();
        if (last == null || last.getTemperature() == null) return;

        double t = last.getTemperature();
        double min = measurementService.getMinT();
        double max = measurementService.getMaxT();

        boolean outOfRange = (t > max); // 🔴 seulement supérieur à 8

        if (!outOfRange) {
            // si température redevient normale → reset
            lastAlertTime = null;
            return;
        }

        // si jamais aucune alerte n’a encore été envoyée
        if (lastAlertTime == null) {
            sendAlert(t, min, max, last.getTimestamp());
            lastAlertTime = Instant.now();
            return;
        }

        // vérifier si 1 heure est passée
        long minutes = Duration.between(lastAlertTime, Instant.now()).toMinutes();

        if (minutes >= 60) {
            sendAlert(t, min, max, last.getTimestamp());
            lastAlertTime = Instant.now();
        }
    }

    private void sendAlert(double t, double min, double max, Object date) {

        String message =
                "⚠️ ALERTE TEMPÉRATURE\n" +
                        "Valeur : " + t + " °C\n" +
                        "Seuil max : " + max + " °C\n" +
                        "Date : " + date + "\n\n" +
                        "⚠️ Température élevée depuis plus d'une heure.";

        telegram.send(message);

        email.send(
                "belhajm264@gmail.com",
                "🚨 Alerte température (toutes les 1h)",
                message
        );

        System.out.println("✅ ALERTE envoyée (1h)");
    }
}
