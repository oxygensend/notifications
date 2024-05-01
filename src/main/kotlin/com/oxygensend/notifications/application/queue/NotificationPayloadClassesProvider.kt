package com.oxygensend.notifications.application.queue

import com.oxygensend.notifications.domain.message.NotificationPayload

interface NotificationPayloadClassesProvider {

    fun <T : NotificationPayload> get(): Set<Class<T>>
}