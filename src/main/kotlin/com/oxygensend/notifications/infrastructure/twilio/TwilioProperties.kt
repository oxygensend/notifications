package com.oxygensend.notifications.infrastructure.twilio

import com.oxygensend.notifications.context.config.NotificationProfile.Companion.SMS
import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "notifications.twilio")
@Profile(SMS)
internal data class TwilioProperties(
    @field:NotBlank val accountSid: String,
    @field:NotBlank val authToken: String,
    @field:NotBlank val fromPhoneNumber: String
)
