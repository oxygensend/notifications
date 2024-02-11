package com.oxygensend.notifications.config.properties

import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "notifications")
data class NotificationProperties(
    val services: Set<String>,
    val secret: String,
    val authEnabled: @NotNull Boolean,
    val storeInDatabase: @NotNull Boolean
)