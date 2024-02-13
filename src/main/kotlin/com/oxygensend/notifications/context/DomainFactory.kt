package com.oxygensend.notifications.context

import com.oxygensend.notifications.context.dto.*
import com.oxygensend.notifications.domain.Channel
import com.oxygensend.notifications.domain.Notification
import com.oxygensend.notifications.domain.message.Mail
import com.oxygensend.notifications.domain.message.Message
import com.oxygensend.notifications.domain.message.Sms
import com.oxygensend.notifications.domain.recipient.*
import java.time.LocalDateTime
import java.util.*

class DomainFactory private constructor() {
    companion object {
        fun createRecipient(dto: RecipientDto): Recipient {
            return when (dto) {
                is SmsDto.PhoneDto -> from(dto)
                is MailDto.EmailDto -> from(dto)
                is WhatsappDto.PhoneDto -> from(dto)
                is TelegramDto.ChatDto -> from(dto)
                else -> throw IllegalArgumentException("Unsupported DTO type")
            }
        }

        fun createMessage(dto: MessageDto): Message {
            return when (dto) {
                is MailDto -> from(dto)
                is TelegramDto -> from(dto)
                is WhatsappDto -> from(dto)
                is SmsDto -> from(dto)
                else -> throw IllegalArgumentException("Unsupported DTO type")
            }
        }

        fun createNotification(message: Message, recipient: Recipient, serviceId: String, requestId: String?, createdAt: LocalDateTime?): Notification {
            return when (recipient) {
                is Email -> from(message as Mail, recipient, serviceId, requestId, createdAt)
                is Phone -> from(message as Sms, recipient, serviceId, requestId, createdAt)
                is WhatsappPhone -> from(message as Sms, recipient, serviceId, requestId, createdAt)
                else -> throw IllegalArgumentException("Unsupported recipient type")
            }
        }

        private fun from(mailDto: MailDto): Mail {
            return Mail(mailDto.subject!!, mailDto.body!!)
        }

        private fun from(smsDto: SmsDto): Sms {
            return Sms(smsDto.body!!)
        }

        private fun from(whatsappDto: WhatsappDto): Sms {
            return Sms(whatsappDto.body!!)
        }

        private fun from(telegramDto: TelegramDto): Sms {
            return Sms(telegramDto.body!!)
        }


        private fun from(phone: SmsDto.PhoneDto): Phone {
            return Phone(phone.number!!, phone.code!!, phone.systemId)
        }

        private fun from(email: MailDto.EmailDto): Email {
            return Email(email.address!!, email.systemId)
        }

        private fun from(phone: WhatsappDto.PhoneDto): WhatsappPhone {
            return WhatsappPhone(phone.number!!, phone.systemId)
        }

        private fun from(phone: TelegramDto.ChatDto): Telegram {
            return Telegram(phone.chatId!!, phone.systemId)
        }

        private fun from(message: Mail, recipient: Email, serviceId: String, requestId: String?, createdAt: LocalDateTime?): Notification {
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

        private fun from(message: Sms, recipient: Phone, serviceId: String, requestId: String?, createdAt: LocalDateTime?): Notification {
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

        private fun from(message: Sms, recipient: WhatsappPhone, serviceId: String, requestId: String?, createdAt: LocalDateTime?): Notification {
            return Notification(
                UUID.randomUUID(),
                null,
                message.content,
                recipient.phone,
                recipient.systemId,
                Channel.EMAIL,
                serviceId,
                requestId,
                createdAt ?: LocalDateTime.now()
            );
        }
    }
}

