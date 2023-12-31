package com.oxygensend.notifications.infrastructure.twilio;

import com.oxygensend.notifications.config.TwilioProperties;
import com.twilio.Twilio;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TwilioProperties.class)
public class TwilioConfig {

    @Bean
    TwilioService twilioService(TwilioProperties twilioProperties) {
        Twilio.init(twilioProperties.accountSid(), twilioProperties.authToken());
        return new TwilioService(twilioProperties.fromPhoneNumber());
    }
}
