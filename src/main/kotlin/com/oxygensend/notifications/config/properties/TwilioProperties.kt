package com.oxygensend.notifications.config.properties

import com.oxygensend.notifications.config.NotificationProfile
import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile
import org.springframework.validation.annotation.Validated

@Profile(NotificationProfile.SMS)
@Validated
@ConfigurationProperties(prefix = "twilio")
data class TwilioProperties(
    @field:NotBlank val accountSid: String,
    @field:NotBlank val authToken: String,
    @field:NotBlank val fromPhoneNumber: String
)
