package com.oxygensend.notifications.domain.message

import com.oxygensend.notifications.domain.history.part.NotificationStatus
import java.util.*

data class Progress(val value: Map<Recipient, NotificationProgress>)
data class NotificationProgress(val notificationId: UUID, val status: NotificationStatus)
