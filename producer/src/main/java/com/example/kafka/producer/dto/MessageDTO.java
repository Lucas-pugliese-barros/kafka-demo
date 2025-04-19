package com.example.kafka.producer.dto;

import java.time.Instant;
import java.util.Map;

public record MessageDTO(
        String id,
        String content,
        MessageType type,                     // e.g., "USER_CREATED", "ORDER_PLACED"
        Instant timestamp,
        String source,
        Map<String, String> headers
) {
    public MessageDTO(String id, String content, MessageType type) {
        this(id, content, type, Instant.now(), null, null);
    }
}