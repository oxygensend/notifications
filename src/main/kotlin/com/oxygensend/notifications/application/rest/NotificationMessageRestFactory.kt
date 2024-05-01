package com.oxygensend.notifications.application.rest

import com.oxygensend.notifications.application.rest.dto.*
import com.oxygensend.notifications.domain.channel.internal.InternalMessage
import com.oxygensend.notifications.domain.channel.internal.RecipientId
import com.oxygensend.notifications.domain.channel.mail.Email
import com.oxygensend.notifications.domain.channel.mail.Mail
import com.oxygensend.notifications.domain.channel.sms.Phone
import com.oxygensend.notifications.domain.channel.sms.Sms
import com.oxygensend.notifications.domain.channel.whatsapp.Whatsapp
import com.oxygensend.notifications.domain.channel.whatsapp.WhatsappPhone
import com.oxygensend.notifications.domain.message.NotificationHeaders
import com.oxygensend.notifications.domain.message.NotificationMessage
import com.oxygensend.notifications.domain.message.NotificationPayload
import org.springframework.stereotype.Component

@Component
internal class NotificationMessageRestFactory {

    fun <T : NotificationPayload, C : MessageDto> create(messagePayload: RestMessagePayload<C>): NotificationMessage<T> {
        val headers = createHeaders(messagePayload)
        val payload = createPayload<T, C>(messagePayload)

        return NotificationMessage(headers, payload);
    }

    private fun <C : MessageDto> createHeaders(messagePayload: RestMessagePayload<C>): NotificationHeaders {
        return NotificationHeaders(
            id = messagePayload.id,
            type = messagePayload.content.type(),
            serviceId = messagePayload.serviceId!!,
            timestamp = messagePayload.createdAt,
            requestId = messagePayload.requestId,
            login = messagePayload.login
        )

    }

    private fun <T : NotificationPayload, C : MessageDto> createPayload(messagePayload: RestMessagePayload<C>): T {
        return when (messagePayload.content) {
            is MailDto -> Mail(
                messagePayload.content.subject!!,
                messagePayload.content.body!!,
                messagePayload.content.recipients.map { Email(it.address!!, it.systemId) }.toSet()
            )

            is SmsDto -> Sms(
                messagePayload.content.body!!,
                messagePayload.content.recipients.map { Phone(it.number!!, it.code!!, it.systemId) }.toSet()
            )

            is WhatsappDto -> Whatsapp(
                messagePayload.content.body!!,
                messagePayload.content.recipients.map { WhatsappPhone(it.number!!, it.systemId) }.toSet()
            )

            is InternalMessageDto -> InternalMessage(
                messagePayload.content.content!!,
                messagePayload.content.recipients.map { RecipientId(it.id!!) }.toSet()
            )

            else -> throw IllegalArgumentException("Not found")
        } as T
    }
}