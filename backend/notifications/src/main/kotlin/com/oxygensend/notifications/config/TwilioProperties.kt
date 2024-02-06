package com.oxygensend.notifications.config

import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "twilio")
data class TwilioProperties(
    val accountSid: @NotEmpty String,
    val authToken: @NotEmpty String,
    val fromPhoneNumber: @NotEmpty String
)
