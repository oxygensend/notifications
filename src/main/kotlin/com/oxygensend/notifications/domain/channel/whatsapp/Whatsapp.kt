package com.oxygensend.notifications.domain.channel.whatsapp

import com.oxygensend.notifications.domain.message.NotificationPayload


data class Whatsapp(val content: String, override val recipients: Set<WhatsappPhone>) : NotificationPayload