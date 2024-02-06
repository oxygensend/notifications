package com.oxygensend.notifications.context

import com.oxygensend.notifications.context.dto.MailDto
import com.oxygensend.notifications.context.dto.SmsDto
import com.oxygensend.notifications.context.rest.MessagePayload
import com.oxygensend.notifications.domain.*
import java.util.stream.Collectors

data class MessageCommand<R, C>(
    val content: C,
    val recipients: Set<R>,
    val login: String?,
    val serviceID: String,
    val createdAt: String?
) {
    companion object {
        fun forMail(payload: MessagePayload<MailDto>): MessageCommand<Email, Mail> {
            val recipients = payload.content.emails.stream().map { DomainFactory.from(it) }.collect(Collectors.toSet())
            return MessageCommand(
                DomainFactory.from(payload.content),
                recipients,
                payload.login,
                payload.serviceID,
                payload.createdAt
            )
        }

        fun forSms(payload: MessagePayload<SmsDto>): MessageCommand<Phone, Sms> {
            return MessageCommand(
                DomainFactory.from(payload.content),
                payload.content.phoneNumbers,
                payload.login,
                payload.serviceID,
                payload.createdAt
            )
        }
    }
}
