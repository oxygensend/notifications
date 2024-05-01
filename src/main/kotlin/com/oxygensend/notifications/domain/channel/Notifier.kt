package com.oxygensend.notifications.domain.channel

import com.oxygensend.notifications.domain.message.NotificationPayload
import com.oxygensend.notifications.domain.message.Progress

interface Notifier<T : NotificationPayload> {

    fun notify(message: T): Progress
}