package com.oxygensend.notifications.context.queue

import com.oxygensend.notifications.domain.Notification

interface NotificationListener {
    fun onNotification(notification: Notification?)
}
