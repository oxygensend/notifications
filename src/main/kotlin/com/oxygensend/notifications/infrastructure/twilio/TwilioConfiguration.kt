package com.oxygensend.notifications.infrastructure.twilio

import com.oxygensend.notifications.application.NotificationIdGenerator
import com.oxygensend.notifications.application.config.NotificationProfile
import com.twilio.Twilio
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile(NotificationProfile.SMS)
@Configuration
@EnableConfigurationProperties(TwilioProperties::class)
class TwilioConfiguration {
    @Bean
    internal fun twilioService(twilioProperties: TwilioProperties, notificationIdGenerator: NotificationIdGenerator): TwilioService {
        Twilio.init(twilioProperties.accountSid, twilioProperties.authToken)
        return TwilioService(twilioProperties.fromPhoneNumber, notificationIdGenerator)
    }
}
