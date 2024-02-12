package com.oxygensend.notifications.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document("notifications")
data class Notification(
    @Id val id: UUID,
    val title: String?,
    val content: String,
    val recipient: String,
    val recipientId: String?,
    val channel: Channel,
    val serviceId: String,
    val requestId: String?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val sentAt: LocalDateTime = LocalDateTime.now()
)
