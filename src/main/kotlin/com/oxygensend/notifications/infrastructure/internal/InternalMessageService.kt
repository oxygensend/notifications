package com.oxygensend.notifications.infrastructure.internal

import com.oxygensend.notifications.context.config.NotificationProfile.Companion.INTERNAL
import com.oxygensend.notifications.domain.Channel
import com.oxygensend.notifications.domain.service.DomainFactory
import com.oxygensend.notifications.domain.service.MessageService
import com.oxygensend.notifications.domain.NotificationRepository
import com.oxygensend.notifications.domain.message.InternalMessage
import com.oxygensend.notifications.domain.recipient.RecipientId
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Profile(INTERNAL)
@Service
internal class InternalMessageService(
    private val notificationRepository: NotificationRepository
) : MessageService<RecipientId, InternalMessage> {

    override fun send(message: InternalMessage, recipients: Set<RecipientId>) {
        // for now messages are not sent in real time
    }

    override fun channel(): Channel {
        return Channel.INTERNAL
    }

    override fun save(message: InternalMessage, recipients: Set<RecipientId>, serviceID: String, requestId: String?, createdAt: LocalDateTime?): Int {
        return recipients.map { DomainFactory.createNotification(message, it, serviceID, requestId, createdAt) }
            .apply { notificationRepository.saveAll(this) }
            .count()
    }
}