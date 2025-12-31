package com.example.coldchain.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramService {

    // ⚠️ remplace par TON vrai token
    private static final String TOKEN = "8553595083:AAFWarV1M4QtgAEWw56UKe3s3u4QDaWnRbg";

    // ⚠️ ton chat_id (numérique, ex: 123456789)
    private static final String CHAT_ID = "1176036173";

    public void send(String message) {

        String url = "https://api.telegram.org/bot" + TOKEN + "/sendMessage";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("chat_id", CHAT_ID);
        body.put("text", message);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, request, String.class);
    }
}
