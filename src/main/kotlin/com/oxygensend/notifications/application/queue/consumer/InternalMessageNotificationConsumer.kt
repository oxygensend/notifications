package com.oxygensend.notifications.application.queue.consumer

import com.oxygensend.notifications.domain.history.NotificationHistoryRepository
import com.oxygensend.notifications.domain.channel.Notifier
import com.oxygensend.notifications.domain.channel.internal.InternalMessage
import com.oxygensend.notifications.domain.message.NotificationConsumer
import com.oxygensend.notifications.domain.message.NotificationMessage
import org.springframework.stereotype.Component

@Component
final class InternalMessageNotificationConsumer(
    private val notifier: Notifier<InternalMessage>,
    private val notificationHistoryRepository: NotificationHistoryRepository
) : NotificationConsumer<InternalMessage> {
    override fun consume(message: NotificationMessage<InternalMessage>) {
        val progress = notifier.notify(message.payload)
        notificationHistoryRepository.save(message, progress)
    }

}