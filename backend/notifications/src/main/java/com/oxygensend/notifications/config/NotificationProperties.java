package com.oxygensend.notifications.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "notifications")
public record NotificationProperties(Set<String> services,
                                     String secret,
                                     boolean authEnabled
) {
}
