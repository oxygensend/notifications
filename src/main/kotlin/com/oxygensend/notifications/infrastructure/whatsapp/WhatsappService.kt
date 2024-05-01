package com.oxygensend.notifications.infrastructure.whatsapp

import com.oxygensend.notifications.application.NotificationIdGenerator
import com.oxygensend.notifications.domain.history.part.NotificationStatus
import com.oxygensend.notifications.domain.channel.Notifier
import com.oxygensend.notifications.domain.channel.whatsapp.Whatsapp
import com.oxygensend.notifications.domain.message.NotificationProgress
import com.oxygensend.notifications.domain.message.Progress
import com.oxygensend.notifications.domain.message.Recipient
import com.whatsapp.api.domain.messages.Message
import com.whatsapp.api.domain.messages.TextMessage
import com.whatsapp.api.impl.WhatsappBusinessCloudApi

internal class WhatsappService(
    private val whatsAppApi: WhatsappBusinessCloudApi,
    private val phoneNumberId: String,
    private val notificationIdGenerator: NotificationIdGenerator
) : Notifier<Whatsapp> {
    override fun notify(message: Whatsapp): Progress {
        val notificationsProgress = HashMap<Recipient, NotificationProgress>()
        message.recipients.forEach {
            val notificationId = notificationIdGenerator.get()
            try {
                val whatsappMessage = Message.MessageBuilder.builder()
                    .setTo(it.phone)
                    .buildTextMessage(TextMessage().setBody(message.content))
                whatsAppApi.sendMessage(phoneNumberId, whatsappMessage)
                notificationsProgress[it] = NotificationProgress(notificationId, NotificationStatus.SENT)
            } catch (ex: Exception) {
                notificationsProgress[it] = NotificationProgress(notificationId, NotificationStatus.SENT)
            }
        }

        return Progress(notificationsProgress)
    }

}
