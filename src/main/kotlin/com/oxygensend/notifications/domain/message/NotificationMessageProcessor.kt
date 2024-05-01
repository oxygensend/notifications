package com.oxygensend.notifications.domain.message

interface NotificationMessageProcessor {
    fun <T : NotificationPayload> process(message: NotificationMessage<T>)
    fun <T : NotificationPayload> processSynchronously(message: NotificationMessage<T>)
}