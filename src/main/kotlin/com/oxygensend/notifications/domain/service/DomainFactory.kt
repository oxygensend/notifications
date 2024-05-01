package com.oxygensend.notifications.domain.service

import com.oxygensend.notifications.domain.history.Notification
import com.oxygensend.notifications.domain.history.part.NotificationStatus
import com.oxygensend.notifications.domain.history.part.Channel
import com.oxygensend.notifications.domain.channel.internal.InternalMessage
import com.oxygensend.notifications.domain.channel.internal.RecipientId
import com.oxygensend.notifications.domain.channel.mail.Email
import com.oxygensend.notifications.domain.channel.mail.Mail
import com.oxygensend.notifications.domain.channel.sms.Phone
import com.oxygensend.notifications.domain.channel.sms.Sms
import com.oxygensend.notifications.domain.channel.whatsapp.Whatsapp
import com.oxygensend.notifications.domain.channel.whatsapp.WhatsappPhone
import com.oxygensend.notifications.domain.message.NotificationMessage
import com.oxygensend.notifications.domain.message.Recipient
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class DomainFactory private constructor() {
    companion object {

        fun createNotification(id: UUID, message: NotificationMessage<*>, recipient: Recipient, status: NotificationStatus): Notification {
            return when (recipient) {
                is Email -> from(id, message as NotificationMessage<Mail>, recipient, status)
                is Phone -> from(id, message as NotificationMessage<Sms>, recipient, status)
                is WhatsappPhone -> from(id, message as NotificationMessage<Whatsapp>, recipient, status)
                is RecipientId -> from(id, message as NotificationMessage<InternalMessage>, recipient, status)
                else -> throw IllegalArgumentException("Unsupported recipient type")
            }
        }

        private fun from(id: UUID, message: NotificationMessage<Mail>, recipient: Email, status: NotificationStatus): Notification {
            return Notification(
                id = id,
                title = message.payload.subject,
                content = message.payload.body,
                recipient = recipient.email,
                recipientId = recipient.systemId,
                channel = Channel.EMAIL,
                status = status,
                serviceId = message.headers.serviceId,
                requestId = message.headers.requestId,
                createdAt = createdAt(message.headers.timestamp)
            );
        }

        private fun from(id: UUID, message: NotificationMessage<Sms>, recipient: Phone, status: NotificationStatus): Notification {
            return Notification(
                id = id,
                content = message.payload.content,
                recipient = recipient.fullNumber(),
                recipientId = recipient.systemId,
                channel = Channel.SMS,
                status = status,
                serviceId = message.headers.serviceId,
                requestId = message.headers.requestId,
                createdAt = createdAt(message.headers.timestamp)
            );
        }

        private fun from(id: UUID, message: NotificationMessage<Whatsapp>, recipient: WhatsappPhone, status: NotificationStatus): Notification {
            return Notification(
                id = id,
                content = message.payload.content,
                recipient = recipient.phone,
                recipientId = recipient.systemId,
                channel = Channel.WHATSAPP,
                status = status,
                serviceId = message.headers.serviceId,
                requestId = message.headers.requestId,
                createdAt = createdAt(message.headers.timestamp)
            );
        }

        private fun from(id: UUID, message: NotificationMessage<InternalMessage>, recipient: RecipientId, status: NotificationStatus): Notification {
            return Notification(
                id = id,
                content = message.payload.content,
                recipient = recipient.id,
                recipientId = recipient.id,
                channel = Channel.INTERNAL,
                status = status,
                serviceId = message.headers.serviceId,
                requestId = message.headers.requestId,
                createdAt = createdAt(message.headers.timestamp)
            );
        }


        private fun createdAt(timestamp: Instant?): LocalDateTime {
            return if (timestamp != null) {
                LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault())
            } else {
                LocalDateTime.now()
            }
        }

    }
}

