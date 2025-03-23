package com.oxygensend.notifications.application.authentication.provider

import com.oxygensend.notifications.application.authentication.HashAlgorithm
import com.oxygensend.notifications.application.config.NotificationsProperties
import org.springframework.stereotype.Component

@Component
internal class AuthPropertiesProvider(private val notificationsProperties: NotificationsProperties) {

    fun getAuthProperties(login: String): Map<String, String?> {
        return when (notificationsProperties.auth.hashAlgorithm) {
            HashAlgorithm.NONE -> createPlainAuthParameters(login)
            else -> createHashingAuthParameters(login)
        }
    }

    private fun createPlainAuthParameters(login: String): Map<String, String?> {
        return mapOf(
            "password" to login,
            "secret" to notificationsProperties.auth.secret
        )
    }

    private fun createHashingAuthParameters(login: String): Map<String, String?> {
        return mapOf(
            "hashedPassword" to login,
            "password" to notificationsProperties.auth.secret,
            "hashingAlgorithm" to notificationsProperties.auth.hashAlgorithm.value
        )
    }
}
