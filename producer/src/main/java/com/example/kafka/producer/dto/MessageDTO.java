package com.example.kafka.producer.dto;

import java.time.Instant;
import java.util.Map;

public record MessageDTO(
        String id,
        String content,
        String type,                     // e.g., "USER_CREATED", "ORDER_PLACED"
        Instant timestamp,
        String source,
        Map<String, String> headers
) {}