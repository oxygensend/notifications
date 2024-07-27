package com.oxygensend.notifications.application.queue.consumer

import com.oxygensend.notifications.application.config.NotificationProfile.Companion.WHATSAPP
import com.oxygensend.notifications.domain.channel.Notifier
import com.oxygensend.notifications.domain.channel.whatsapp.Whatsapp
import com.oxygensend.notifications.domain.history.NotificationHistoryRepository
import com.oxygensend.notifications.domain.message.NotificationConsumer
import com.oxygensend.notifications.domain.message.NotificationMessage
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile(WHATSAPP)
@Component
final class WhatsappNotificationConsumer(
    private val notifier: Notifier<Whatsapp>,
    private val notificationHistoryRepository: NotificationHistoryRepository
) : NotificationConsumer<Whatsapp> {
    override fun consume(message: NotificationMessage<Whatsapp>) {
        val progress = notifier.notify(message.payload)
        notificationHistoryRepository.save(message, progress)
    }
}

