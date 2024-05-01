package com.oxygensend.notifications.domain.channel.internal

import com.oxygensend.notifications.domain.message.NotificationPayload

data class InternalMessage(
    val content: String,
    override val recipients: Set<RecipientId>
) : NotificationPayload
