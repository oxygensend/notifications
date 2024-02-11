package com.oxygensend.notifications.context.authentication

interface Authentication {
    @Throws(AuthException::class)
    fun authenticate(params: Map<String, String?>)
}