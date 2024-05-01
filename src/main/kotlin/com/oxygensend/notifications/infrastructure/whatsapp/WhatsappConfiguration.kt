package com.oxygensend.notifications.infrastructure.whatsapp

import com.oxygensend.notifications.application.NotificationIdGenerator
import com.oxygensend.notifications.application.config.NotificationProfile
import com.whatsapp.api.WhatsappApiFactory
import com.whatsapp.api.impl.WhatsappBusinessCloudApi
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile(NotificationProfile.WHATSAPP)
@Configuration
@EnableConfigurationProperties(WhatsappProperties::class)
class WhatsappConfiguration {

    @Bean
    internal fun whatsappBusinessCloudApi(whatsappProperties: WhatsappProperties): WhatsappBusinessCloudApi {
        return WhatsappApiFactory.newInstance(whatsappProperties.apiKey).newBusinessCloudApi()
    }

    @Bean
    internal fun whatsappService(
        whatsappBusinessCloudApi: WhatsappBusinessCloudApi,
        whatsappProperties: WhatsappProperties,
        notificationIdGenerator: NotificationIdGenerator
    ): WhatsappService {
        return WhatsappService(whatsappBusinessCloudApi, whatsappProperties.phoneNumberId, notificationIdGenerator)
    }
}
