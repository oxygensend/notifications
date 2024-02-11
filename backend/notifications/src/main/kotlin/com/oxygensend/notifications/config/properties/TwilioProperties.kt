package com.oxygensend.notifications.config.properties

import com.oxygensend.notifications.config.NotificationProfile
import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile
import org.springframework.validation.annotation.Validated

@Profile(NotificationProfile.SMS)
@Validated
@ConfigurationProperties(prefix = "twilio")
data class TwilioProperties(
    val accountSid: @NotEmpty String,
    val authToken: @NotEmpty String,
    val fromPhoneNumber: @NotEmpty String
)
