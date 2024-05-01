package com.oxygensend.notifications.infrastructure.internal

import com.oxygensend.notifications.application.NotificationIdGenerator
import com.oxygensend.notifications.application.config.NotificationProfile.Companion.INTERNAL
import com.oxygensend.notifications.domain.channel.Notifier
import com.oxygensend.notifications.domain.channel.internal.InternalMessage
import com.oxygensend.notifications.domain.history.part.NotificationStatus
import com.oxygensend.notifications.domain.message.NotificationProgress
import com.oxygensend.notifications.domain.message.Progress
import com.oxygensend.notifications.domain.message.Recipient
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile(INTERNAL)
@Service
internal class InternalMessageService(
    private val notificationIdGenerator: NotificationIdGenerator
) : Notifier<InternalMessage> {

    private val logger = LoggerFactory.getLogger(this::class.java)
    override fun notify(message: InternalMessage): Progress {
        val notificationsProgress = HashMap<Recipient, NotificationProgress>()
        message.recipients.forEach {
            val notificationId = notificationIdGenerator.get()
            notificationsProgress[it] = NotificationProgress(notificationId, NotificationStatus.SENT)
            logger.info("INTERNAL message sent successfully {} to {}", notificationId, it)
        }
        return Progress(notificationsProgress)
    }
}