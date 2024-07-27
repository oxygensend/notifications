package com.oxygensend.notifications.application.queue.consumer

import com.oxygensend.notifications.application.config.NotificationProfile.Companion.MAIL
import com.oxygensend.notifications.domain.history.NotificationHistoryRepository
import com.oxygensend.notifications.domain.channel.Notifier
import com.oxygensend.notifications.domain.channel.mail.Mail
import com.oxygensend.notifications.domain.message.NotificationConsumer
import com.oxygensend.notifications.domain.message.NotificationMessage
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component


@Profile(MAIL)
@Component
final class MailNotificationConsumer(
    private val notifier: Notifier<Mail>,
    private val notificationHistoryRepository: NotificationHistoryRepository
) : NotificationConsumer<Mail> {

    override fun consume(message: NotificationMessage<Mail>) {
        val progress = notifier.notify(message.payload)
        notificationHistoryRepository.save(message, progress)
    }
}