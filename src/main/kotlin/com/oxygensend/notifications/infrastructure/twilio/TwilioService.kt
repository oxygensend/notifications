package com.oxygensend.notifications.infrastructure.twilio

import com.oxygensend.notifications.application.NotificationIdGenerator
import com.oxygensend.notifications.domain.history.part.NotificationStatus
import com.oxygensend.notifications.domain.channel.Notifier
import com.oxygensend.notifications.domain.channel.sms.Sms
import com.oxygensend.notifications.domain.message.NotificationProgress
import com.oxygensend.notifications.domain.message.Progress
import com.oxygensend.notifications.domain.message.Recipient
import com.twilio.exception.ApiException
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.slf4j.LoggerFactory

internal class TwilioService(
    private val fromPhoneNumber: String,
    private val notificationIdGenerator: NotificationIdGenerator
) : Notifier<Sms> {
    private val logger = LoggerFactory.getLogger(TwilioService::class.java)
    override fun notify(message: Sms): Progress {
        val notificationsProgress = HashMap<Recipient, NotificationProgress>()
        for (phoneNumber in message.recipients) {
            val notificationId = notificationIdGenerator.get()
            try {
                Message.creator(
                    PhoneNumber(phoneNumber.fullNumber()),
                    PhoneNumber(fromPhoneNumber),
                    message.content
                ).create()
                notificationsProgress[phoneNumber] = NotificationProgress(notificationId, NotificationStatus.SENT)
                logger.info("SMS message sent successfully {} to {}", message.content, phoneNumber)
            } catch (e: ApiException) {
                notificationsProgress[phoneNumber] = NotificationProgress(notificationId, NotificationStatus.FAILED)
                logger.info("Error sending SMS message: {} , error message: {}", message, e.message)
                throw RuntimeException(e.message)
            }
        }
        return Progress(notificationsProgress)
    }
}
