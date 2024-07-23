package com.oxygensend.notifications.infrastructure.mongo

import com.oxygensend.notifications.domain.history.part.Channel
import com.oxygensend.notifications.domain.history.part.NotificationStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document("notifications")
internal data class NotificationMongo(
    @Id val id: UUID,
    val title: String?,
    val content: String,
    val recipient: String,
    val recipientId: String?,
    val channel: Channel,
    val status: NotificationStatus,
    val serviceId: String,
    val requestId: String?,
    val deleted: Boolean,
    val createdAt: LocalDateTime,
    val sentAt: LocalDateTime,
    val seenAt: LocalDateTime?
)

