package com.oxygensend.notifications.application.authentication.provider

internal interface Authentication {
    @Throws(AuthException::class)
    fun authenticate(params: Map<String, String?>)
}