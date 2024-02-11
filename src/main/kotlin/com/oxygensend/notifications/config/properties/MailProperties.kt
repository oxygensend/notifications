package com.oxygensend.notifications.config.properties

import com.oxygensend.notifications.config.NotificationProfile
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile
import org.springframework.validation.annotation.Validated

@Profile(NotificationProfile.MAIL)
@Validated
@ConfigurationProperties(prefix = "mail")
data class MailProperties(

    @field:NotBlank val host: String,
    @field:NotNull val port: Int,
    val username: String?,
    val password: String?,
    @field:NotBlank val protocol: String,
    val auth: Boolean,
    val starttlsEnable: Boolean,
    val debug: Boolean,
    val testConnection: Boolean,
    @field:NotBlank val emailFrom: String
)
