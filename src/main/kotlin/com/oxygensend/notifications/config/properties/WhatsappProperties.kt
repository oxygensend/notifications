package com.oxygensend.notifications.config.properties

import com.oxygensend.notifications.config.NotificationProfile.Companion.WHATSAPP
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "notifications.whatsapp")
@Profile(WHATSAPP)
data class WhatsappProperties(
    @field:NotNull @field:NotBlank val apiKey: String,
    @field:NotNull @field:NotBlank val phoneNumberId: String
)