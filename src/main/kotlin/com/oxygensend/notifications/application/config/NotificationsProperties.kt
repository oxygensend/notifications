package com.oxygensend.notifications.application.config

interface NotificationsProperties {
    val services: Set<String>
    val storeInDatabase: Boolean
    val auth: AuthProperties
}