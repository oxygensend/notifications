package com.oxygensend.notifications.infrastructure.whatsapp

import com.oxygensend.notifications.config.NotificationProfile
import com.oxygensend.notifications.config.properties.WhatsappProperties
import com.oxygensend.notifications.domain.NotificationRepository
import com.whatsapp.api.WhatsappApiFactory
import com.whatsapp.api.impl.WhatsappBusinessCloudApi
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile(NotificationProfile.WHATSAPP)
@Configuration
@EnableConfigurationProperties(WhatsappProperties::class)
internal class WhatsappConfiguration {

    @Bean
    fun whatsappBusinessCloudApi(whatsappProperties: WhatsappProperties): WhatsappBusinessCloudApi {
        return WhatsappApiFactory.newInstance(whatsappProperties.apiKey).newBusinessCloudApi()
    }

    @Bean
    fun whatsappService(
        whatsappBusinessCloudApi: WhatsappBusinessCloudApi,
        notificationRepository: NotificationRepository,
        whatsappProperties: WhatsappProperties
    ): WhatsappService {
        return WhatsappService(whatsappBusinessCloudApi, notificationRepository, whatsappProperties.phoneNumberId)
    }
}
