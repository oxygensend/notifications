package com.oxygensend.notifications.application.authentication

import com.oxygensend.notifications.application.config.NotificationsProperties
import com.oxygensend.notifications.domain.message.NotificationHeaders
import com.oxygensend.notifications.domain.exception.ForbiddenException
import com.oxygensend.notifications.domain.exception.UnauthorizedException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val notificationsProperties: NotificationsProperties,
    private val authentication: Authentication
) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(this::class.java)
    }

    fun authenticate(headers: NotificationHeaders) {
        if (!checkIfAuthenticationIsEnabled()) {
            return
        }
        loginAuthentication(headers)
        checkServiceAccess(headers)
    }

    private fun checkIfAuthenticationIsEnabled(): Boolean {
        if (!notificationsProperties.authEnabled) {
            LOGGER.info("Authentication disabled, skipping")
            return false
        }

        return true
    }

    private fun checkServiceAccess(headers: NotificationHeaders) {
        if (!notificationsProperties.services.contains(headers.serviceId)) {
            LOGGER.error("Authorization failed, unauthorized service: {}", headers.serviceId)
            throw ForbiddenException("Access denied for service:  ${headers.serviceId}")
        }
    }

    private fun loginAuthentication(headers: NotificationHeaders) {
        if (headers.login == null) {
            LOGGER.error("Authentication failed, login is required")
            throw UnauthorizedException("Login is required")
        }
        try {
            authentication.authenticate(getAuthParameters(headers))
        } catch (e: AuthException) {
            throw UnauthorizedException(e.message ?: "Unauthorized")
        }
    }

    private fun getAuthParameters(headers: NotificationHeaders): Map<String, String?> {
        return mapOf("password" to headers.login, "hashedPassword" to notificationsProperties.secret)
    }

}