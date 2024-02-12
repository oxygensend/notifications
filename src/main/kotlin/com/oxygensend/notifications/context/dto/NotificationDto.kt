package com.oxygensend.notifications.context.dto

import com.oxygensend.notifications.domain.Notification
import com.oxygensend.notifications.domain.Channel
import java.time.LocalDateTime
import java.util.*

data class NotificationDto(
    val id: UUID,
    val title: String?,
    val content: String,
    val recipient: String,
    val recipientId: String?,
    val channel: Channel,
    val serviceId: String,
    val requestId: String?,
    val createdAt: LocalDateTime,
    val sentAt: LocalDateTime
) {
    companion object {
        fun fromDomain(notification: Notification): NotificationDto {
            return NotificationDto(
                id = notification.id,
                title = notification.title,
                content = notification.content,
                recipient = notification.recipient,
                recipientId = notification.recipientId,
                channel = notification.channel,
                serviceId = notification.serviceId,
                requestId = notification.requestId,
                createdAt = notification.createdAt,
                sentAt = notification.sentAt
            )
        }
    }
}
