package com.oxygensend.notifications.application.authentication.provider

import org.springframework.stereotype.Component

@Component
class AuthenticationStrategy internal constructor(
    private val authPropertiesProvider: AuthPropertiesProvider,
    authenticationStrategySelector: AuthenticationStrategySelector
) {
    private final val authentication: Authentication = authenticationStrategySelector.getAuthenticationStrategy();

    fun authenticate(login: String) {
        val authProperties = authPropertiesProvider.getAuthProperties(login)
        authentication.authenticate(authProperties)
    }
}
