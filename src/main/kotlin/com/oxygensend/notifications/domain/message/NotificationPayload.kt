package com.oxygensend.notifications.domain.message

interface NotificationPayload {
    val recipients: Set<Recipient>
}