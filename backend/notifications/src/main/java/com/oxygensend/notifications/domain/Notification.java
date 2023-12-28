package com.oxygensend.notifications.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record Notification(UUID id,
                           String title,
                           String content,
                           String recipient,
                           Channel channel,
                           LocalDateTime createdAt,
                           LocalDateTime sentAt) {
}
