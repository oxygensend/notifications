package com.oxygensend.notifications.domain.channel.sms

import com.oxygensend.notifications.domain.message.NotificationPayload

data class Sms(val content: String, override val recipients: Set<Phone>) : NotificationPayload
