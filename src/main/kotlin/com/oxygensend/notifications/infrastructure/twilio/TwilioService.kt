package com.oxygensend.notifications.infrastructure.twilio

import com.oxygensend.notifications.application.NotificationIdGenerator
import com.oxygensend.notifications.domain.channel.Notifier
import com.oxygensend.notifications.domain.channel.sms.Sms
import com.oxygensend.notifications.domain.history.part.NotificationStatus
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
        message.recipients.forEach {
            val notificationId = notificationIdGenerator.get()
            try {
                Message.creator(
                    PhoneNumber(it.fullNumber()),
                    PhoneNumber(fromPhoneNumber),
                    message.content
                ).create()
                notificationsProgress[it] = NotificationProgress(notificationId, NotificationStatus.SENT)
                logger.info("SMS message sent successfully {} to {}", notificationId, it)
            } catch (e: ApiException) {
                notificationsProgress[it] = NotificationProgress(notificationId, NotificationStatus.FAILED)
                logger.info("Error sending SMS message: {} to {} , error message: {}", notificationId, it, e.message)
            }
        }
        return Progress(notificationsProgress)
    }
}
