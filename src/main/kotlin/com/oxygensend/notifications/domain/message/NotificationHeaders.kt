package com.oxygensend.notifications.domain.message

import java.time.Instant

data class NotificationHeaders(
    val id: String?,
    val type: String,
    val serviceId: String ,
    val timestamp: Instant? = null,
    val login: String? = null,
    val requestId: String? = null,
)
