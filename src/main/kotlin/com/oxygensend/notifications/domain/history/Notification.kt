package com.oxygensend.notifications.domain.history

import com.oxygensend.notifications.domain.history.part.Channel
import com.oxygensend.notifications.domain.history.part.NotificationStatus
import java.time.LocalDateTime
import java.util.*

data class Notification(
    val id: UUID,
    val title: String? = null,
    val content: String,
    val recipient: String,
    val recipientId: String?,
    val channel: Channel,
    val status: NotificationStatus,
    val serviceId: String,
    val requestId: String?,
    val deleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val sentAt: LocalDateTime = LocalDateTime.now(),
    val seenAt: LocalDateTime? = null
) {
    fun markAsSeen() = copy(seenAt = LocalDateTime.now())

    fun delete() = copy(deleted = true)
}
