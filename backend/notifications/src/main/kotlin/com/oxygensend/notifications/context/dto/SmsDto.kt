package com.oxygensend.notifications.context.dto

import com.oxygensend.notifications.domain.PhoneCode
import jakarta.validation.constraints.NotEmpty

data class SmsDto(val body: @NotEmpty String, val phoneNumbers: Set<PhoneDto>) {
    data class PhoneDto(val number: String, val code: PhoneCode, val systemId: String? = null)
}
