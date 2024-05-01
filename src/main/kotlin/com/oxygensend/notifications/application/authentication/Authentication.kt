package com.oxygensend.notifications.application.authentication

interface Authentication {
    @Throws(AuthException::class)
    fun authenticate(params: Map<String, String?>)
}