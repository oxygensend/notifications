package com.oxygensend.notifications.infrastructure.twilio

import com.oxygensend.notifications.context.MessageService
import com.oxygensend.notifications.context.DomainFactory
import com.oxygensend.notifications.domain.*
import com.oxygensend.notifications.domain.Channel
import com.oxygensend.notifications.domain.recipient.Phone
import com.oxygensend.notifications.domain.message.Sms
import com.twilio.exception.ApiException
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class TwilioService(private val fromPhoneNumber: String, private val notificationRepository: NotificationRepository) : MessageService<Phone, Sms> {

    private val logger = LoggerFactory.getLogger(TwilioService::class.java)
    override fun send(message: Sms, recipients: Set<Phone>) {
        try {
            for (phoneNumber in recipients) {
                Message.creator(
                    PhoneNumber(phoneNumber.fullNumber()),
                    PhoneNumber(fromPhoneNumber),
                    message.content
                ).create()
                logger.info("SMS message sent successfully {} to {}", message, phoneNumber)
            }
        } catch (e: ApiException) {
            logger.info("Error sending SMS message: {} to {}, error message: {}", message, recipients, e.message)
            throw RuntimeException(e.message)
        }
    }

    override fun channel(): Channel {
        return Channel.SMS
    }

    override fun save(message: Sms, recipients: Set<Phone>, serviceID: String, requestId: String?, createdAt: LocalDateTime?): Int {
        return recipients.map { DomainFactory.createNotification(message, it, serviceID, requestId, createdAt) }
            .apply { notificationRepository.saveAll(this) }
            .count()
    }

}
