package com.oxygensend.notifications.domain.channel.sms

import com.oxygensend.notifications.domain.message.Recipient


data class Phone(val number: String, val code: PhoneCode, val systemId: String? = null): Recipient {
    fun fullNumber(): String {
        return code.code + number
    }
}
