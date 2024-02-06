package com.oxygensend.notifications.domain

import java.time.LocalDateTime
import java.util.UUID

data class Notification(
    val id: UUID,
    val title: String,
    val content: String,
    val recipient: String,
    val channel: Channel,
    val createdAt: LocalDateTime,
    val sentAt: LocalDateTime
)