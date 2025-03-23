package com.oxygensend.notifications.application.authentication.provider

import org.springframework.stereotype.Component

@Component
internal class PlainAuthentication : Authentication {
    override fun authenticate(params: Map<String, String?>) {
        val plainPassword = params["password"] ?: throw IllegalArgumentException("Password is required")
        val secret = params["secret"] ?: throw IllegalArgumentException("Secret is required")

        if (plainPassword == secret) {
            return
        }

        throw AuthException("Invalid password")
    }
}