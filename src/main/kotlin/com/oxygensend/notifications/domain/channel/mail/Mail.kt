package com.oxygensend.notifications.domain.channel.mail

import com.oxygensend.notifications.domain.message.NotificationPayload

data class Mail(val subject: String,
                val body: String,
                override val recipients: Set<Email>) : NotificationPayload
