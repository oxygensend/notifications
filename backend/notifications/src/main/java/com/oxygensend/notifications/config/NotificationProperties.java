package com.oxygensend.notifications.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
@ConfigurationProperties(prefix = "notifications")
public record NotificationProperties(Set<String> services,
                                     String secret,
                                     @NotNull
                                     boolean authEnabled
) {
}
