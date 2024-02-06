package com.oxygensend.notifications.domain

import com.oxygensend.notifications.context.dto.MailDto
import com.oxygensend.notifications.context.dto.SmsDto

class DomainFactory {
    companion object {
        @JvmStatic
        fun from(mailDto: MailDto): Mail {
            return Mail(mailDto.subject, mailDto.body)
        }

        @JvmStatic
        fun from(smsDto: SmsDto): Sms {
            return Sms(smsDto.body)
        }

        @JvmStatic
        fun from(email: String?): Email {
            return Email(email!!)
        }

    }
}
