package com.oxygensend.notifications.domain

data class Phone(val number: String, val code: PhoneCode) {
    fun fullNumber(): String {
        return code.code + number
    }
}
