package com.oxygensend.notifications.infrastructure.twilio

import com.oxygensend.notifications.context.MessageService
import com.oxygensend.notifications.domain.Channel
import com.oxygensend.notifications.domain.Phone
import com.oxygensend.notifications.domain.Sms
import com.twilio.exception.ApiException
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.slf4j.LoggerFactory

class TwilioService(private val fromPhoneNumber: String) : MessageService<Phone, Sms> {

    private val logger = LoggerFactory.getLogger(TwilioService::class.java)
    override fun send(message: Sms, recipients: Set<Phone>) {
        try {
            for (phoneNumber in recipients) {
                Message.creator(
                    PhoneNumber(phoneNumber.fullNumber()),
                    PhoneNumber(fromPhoneNumber),
                    message.content).create()
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

}
