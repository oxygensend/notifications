package com.oxygensend.notifications.domain.message

data class NotificationMessage<T : NotificationPayload>(
    val headers: NotificationHeaders,
    val payload: T
)
