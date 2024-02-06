package com.oxygensend.notifications.config

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "mail")
data class MailProperties(
    val host: @NotEmpty String,
    val port: @NotNull Int,
    val username: String?,
    val password: String?,
    val protocol: @NotEmpty String,
    val auth: Boolean,
    val starttlsEnable: Boolean,
    val debug: Boolean,
    val testConnection: Boolean,
    val emailFrom: @NotEmpty String
)
