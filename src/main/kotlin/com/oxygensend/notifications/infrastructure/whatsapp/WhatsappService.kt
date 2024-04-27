package com.oxygensend.notifications.infrastructure.whatsapp

import com.oxygensend.notifications.domain.service.DomainFactory
import com.oxygensend.notifications.domain.service.MessageService
import com.oxygensend.notifications.domain.Channel
import com.oxygensend.notifications.domain.NotificationRepository
import com.oxygensend.notifications.domain.message.Sms
import com.oxygensend.notifications.domain.recipient.WhatsappPhone
import com.whatsapp.api.domain.messages.Message
import com.whatsapp.api.domain.messages.TextMessage
import com.whatsapp.api.impl.WhatsappBusinessCloudApi
import java.time.LocalDateTime

internal class WhatsappService(
    private val whatsAppApi: WhatsappBusinessCloudApi,
    private val notificationRepository: NotificationRepository,
    private val phoneNumberId: String
) :
    MessageService<WhatsappPhone, Sms> {

    override fun send(message: Sms, recipients: Set<WhatsappPhone>) {
        recipients.forEach {
            val whatsappMessage = Message.MessageBuilder.builder()
                .setTo(it.phone)
                .buildTextMessage(TextMessage().setBody(message.content))
            whatsAppApi.sendMessage(phoneNumberId, whatsappMessage)
        }
    }

    override fun channel(): Channel {
        return Channel.WHATSAPP
    }

    override fun save(message: Sms, recipients: Set<WhatsappPhone>, serviceID: String, requestId: String?, createdAt: LocalDateTime?): Int {
        return recipients.map { DomainFactory.createNotification(message, it, serviceID, requestId, createdAt) }
            .apply { notificationRepository.saveAll(this) }
            .count()
    }
}