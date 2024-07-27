package com.oxygensend.notifications.infrastructure.whatsapp

import com.oxygensend.notifications.application.NotificationIdGenerator
import com.oxygensend.notifications.application.config.NotificationProfile.Companion.WHATSAPP
import com.oxygensend.notifications.domain.channel.Notifier
import com.oxygensend.notifications.domain.channel.whatsapp.Whatsapp
import com.oxygensend.notifications.domain.history.part.NotificationStatus
import com.oxygensend.notifications.domain.message.NotificationProgress
import com.oxygensend.notifications.domain.message.Progress
import com.oxygensend.notifications.domain.message.Recipient
import com.oxygensend.notifications.infrastructure.twilio.TwilioService
import com.whatsapp.api.domain.messages.Message
import com.whatsapp.api.domain.messages.TextMessage
import com.whatsapp.api.impl.WhatsappBusinessCloudApi
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile

@Profile(WHATSAPP)
internal class WhatsappService(
    private val whatsAppApi: WhatsappBusinessCloudApi,
    private val phoneNumberId: String,
    private val notificationIdGenerator: NotificationIdGenerator
) : Notifier<Whatsapp> {
    private val logger = LoggerFactory.getLogger(TwilioService::class.java)
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
                logger.info("WHATSAPP message sent successfully {} to {}", notificationId, it)
            } catch (ex: Exception) {
                notificationsProgress[it] = NotificationProgress(notificationId, NotificationStatus.SENT)
                logger.info("Error sending MAIL message: {} to {} , with message: {}", message, it, ex.message)
            }
        }

        return Progress(notificationsProgress)
    }

}
