package com.oxygensend.notifications.infrastructure

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(SpringNotificationProperties::class)
@Configuration
class AppConfiguration {
}