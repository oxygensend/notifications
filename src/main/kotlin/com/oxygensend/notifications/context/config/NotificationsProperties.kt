package com.oxygensend.notifications.context.config

interface NotificationsProperties {
    val services: Set<String>
    val secret: String
    val authEnabled: Boolean
    val storeInDatabase: Boolean
}