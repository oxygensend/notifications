package com.oxygensend.notifications.domain.communication


data class Phone(val number: String, val code: PhoneCode, val systemId: String? = null) {
    fun fullNumber(): String {
        return code.code + number
    }
}