package com.oxygensend.notifications.infrastructure

import com.oxygensend.notifications.application.authentication.HashAlgorithm
import com.oxygensend.notifications.application.config.AuthProperties
import com.oxygensend.notifications.application.config.NotificationsProperties
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "notifications")
data class SpringNotificationProperties(
    override val services: Set<String>,
    @field:NotNull override val storeInDatabase: Boolean,
    @field:NotNull @field:Valid override val auth: SpringAuthProperties
) : NotificationsProperties


data class SpringAuthProperties(
    @field:NotNull override val enabled: Boolean,
    @field:NotNull override val secret: String,
    @field:NotNull override val hashAlgorithm: HashAlgorithm
) : AuthProperties