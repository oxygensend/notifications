package com.oxygensend.notifications.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "twilio")
public record TwilioProperties(@NotEmpty
                               String accountSid,
                               @NotEmpty
                               String authToken,
                               @NotEmpty
                               String fromPhoneNumber) {
}
