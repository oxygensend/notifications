package com.oxygensend.notifications.application.queue.consumer

import com.oxygensend.notifications.domain.history.NotificationHistoryRepository
import com.oxygensend.notifications.domain.channel.Notifier
import com.oxygensend.notifications.domain.channel.sms.Sms
import com.oxygensend.notifications.domain.message.NotificationConsumer
import com.oxygensend.notifications.domain.message.NotificationMessage
import org.springframework.stereotype.Component

@Component
final class SmsNotificationConsumer(
    private val notifier: Notifier<Sms>,
    private val notificationHistoryRepository: NotificationHistoryRepository
) : NotificationConsumer<Sms> {
    override fun consume(message: NotificationMessage<Sms>) {
        val progress = notifier.notify(message.payload)
        notificationHistoryRepository.save(message, progress)
    }
}

