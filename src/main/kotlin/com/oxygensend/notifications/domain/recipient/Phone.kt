package com.oxygensend.notifications.domain.recipient


data class Phone(val number: String, val code: PhoneCode, val systemId: String? = null): Recipient {
    fun fullNumber(): String {
        return code.code + number
    }
}
