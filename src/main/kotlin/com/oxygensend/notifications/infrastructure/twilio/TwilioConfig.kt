package com.oxygensend.notifications.infrastructure.twilio

import com.oxygensend.notifications.config.NotificationProfile
import com.oxygensend.notifications.config.properties.TwilioProperties
import com.oxygensend.notifications.domain.NotificationRepository
import com.twilio.Twilio
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile(NotificationProfile.SMS)
@Configuration
@EnableConfigurationProperties(TwilioProperties::class)
class TwilioConfig {
    @Bean
    fun twilioService(twilioProperties: TwilioProperties, notificationRepository: NotificationRepository): TwilioService {
        Twilio.init(twilioProperties.accountSid, twilioProperties.authToken)
        return TwilioService(twilioProperties.fromPhoneNumber, notificationRepository)
    }
}
