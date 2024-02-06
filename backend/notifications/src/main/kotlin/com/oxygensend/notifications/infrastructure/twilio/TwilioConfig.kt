package com.oxygensend.notifications.infrastructure.twilio

import com.oxygensend.notifications.config.TwilioProperties
import com.twilio.Twilio
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(TwilioProperties::class)
class TwilioConfig {
    @Bean
    fun twilioService(twilioProperties: TwilioProperties): TwilioService {
        Twilio.init(twilioProperties.accountSid, twilioProperties.authToken)
        return TwilioService(twilioProperties.fromPhoneNumber)
    }
}
