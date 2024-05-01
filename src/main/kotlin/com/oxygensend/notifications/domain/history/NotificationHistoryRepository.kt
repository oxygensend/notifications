package com.oxygensend.notifications.domain.history

import com.oxygensend.notifications.domain.message.NotificationMessage
import com.oxygensend.notifications.domain.message.NotificationPayload
import com.oxygensend.notifications.domain.message.Progress

interface NotificationHistoryRepository {

    fun <T : NotificationPayload> save(notificationMessage: NotificationMessage<T>, progress: Progress)

}