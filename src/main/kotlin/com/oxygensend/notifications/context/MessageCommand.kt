package com.oxygensend.notifications.context

import com.oxygensend.notifications.context.dto.MailDto
import com.oxygensend.notifications.context.dto.SmsDto
import com.oxygensend.notifications.context.rest.MessagePayload
import com.oxygensend.notifications.domain.communication.Email
import com.oxygensend.notifications.domain.communication.Mail
import com.oxygensend.notifications.domain.communication.Phone
import com.oxygensend.notifications.domain.communication.Sms
import java.time.LocalDateTime
import java.util.stream.Collectors

data class MessageCommand<R, C>(
    val content: C,
    val recipients: Set<R>,
    val login: String?,
    val serviceId: String,
    val requestId: String?,
    val createdAt: LocalDateTime?
) {
    companion object {
        fun forMail(payload: MessagePayload<MailDto>): MessageCommand<Email, Mail> {
            val recipients = payload.content.emails.stream().map { DomainFactory.from(it) }.collect(Collectors.toSet())
            return MessageCommand(
                DomainFactory.from(payload.content),
                recipients,
                payload.login,
                payload.serviceId,
                payload.requestId,
                payload.createdAt
            )
        }

        fun forSms(payload: MessagePayload<SmsDto>): MessageCommand<Phone, Sms> {
            val recipients = payload.content.phoneNumbers.stream().map { DomainFactory.from(it) }.collect(Collectors.toSet())
            return MessageCommand(
                DomainFactory.from(payload.content),
                recipients,
                payload.login,
                payload.serviceId,
                payload.requestId,
                payload.createdAt
            )
        }
    }
}
