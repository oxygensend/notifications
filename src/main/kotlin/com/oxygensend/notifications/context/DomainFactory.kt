package com.oxygensend.notifications.context

import com.oxygensend.notifications.context.dto.MailDto
import com.oxygensend.notifications.context.dto.SmsDto
import com.oxygensend.notifications.domain.Notification
import com.oxygensend.notifications.domain.communication.*
import java.time.LocalDateTime
import java.util.*

class DomainFactory {
    companion object {
        fun from(mailDto: MailDto): Mail {
            return Mail(mailDto.subject, mailDto.body)
        }

        fun from(smsDto: SmsDto): Sms {
            return Sms(smsDto.body)
        }

        fun from(phone: SmsDto.PhoneDto): Phone {
            return Phone(phone.number, phone.code, phone.systemId)
        }

        fun from(email: MailDto.EmailDto): Email {
            return Email(email.address, email.systemId)
        }

        fun from(message: Mail, recipient: Email, serviceId: String, requestId: String?, createdAt: LocalDateTime?): Notification {
            return Notification(
                UUID.randomUUID(),
                message.subject,
                message.body,
                recipient.address,
                recipient.systemId,
                Channel.EMAIL,
                serviceId,
                requestId,
                createdAt ?: LocalDateTime.now()
            );
        }

        fun from(message: Sms, recipient: Phone, serviceId: String, requestId: String?, createdAt: LocalDateTime?): Notification {
            return Notification(
                UUID.randomUUID(),
                null,
                message.content,
                recipient.fullNumber(),
                recipient.systemId,
                Channel.EMAIL,
                serviceId,
                requestId,
                createdAt ?: LocalDateTime.now()
            );
        }

    }
}
