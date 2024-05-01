package com.oxygensend.notifications.domain.history

import com.oxygensend.notifications.domain.history.part.NotificationStatus
import com.oxygensend.notifications.domain.history.part.Channel
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document("notifications")
data class Notification(
    @Id val id: UUID,
    val title: String? = null,
    val content: String,
    val recipient: String,
    val recipientId: String?,
    val channel: Channel,
    val status: NotificationStatus,
    val serviceId: String,
    val requestId: String?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val sentAt: LocalDateTime = LocalDateTime.now(),
    val seenAt: LocalDateTime? = null
) {
    fun markAsSeen() = copy(seenAt = LocalDateTime.now())
}
