package com.oxygensend.notifications.application.authentication.provider

import com.oxygensend.notifications.application.authentication.HashAlgorithm
import com.oxygensend.notifications.application.config.NotificationsProperties
import org.springframework.stereotype.Component

@Component
internal class AuthenticationStrategySelector(private val notificationsProperties: NotificationsProperties) {

    fun getAuthenticationStrategy(): Authentication {
        return when (notificationsProperties.auth.hashAlgorithm) {
            HashAlgorithm.NONE -> PlainAuthentication()
            else -> HashingAuthentication()
        }
    }
}
