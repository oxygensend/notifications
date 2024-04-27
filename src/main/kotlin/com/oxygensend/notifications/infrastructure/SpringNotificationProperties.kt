package com.oxygensend.notifications.infrastructure

import com.oxygensend.notifications.context.config.NotificationsProperties
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "notifications")
data class SpringNotificationProperties(
    override val services: Set<String>,
    override val secret: String,
    @field:NotNull override val authEnabled: Boolean,
    @field:NotNull override val storeInDatabase: Boolean
) : NotificationsProperties
