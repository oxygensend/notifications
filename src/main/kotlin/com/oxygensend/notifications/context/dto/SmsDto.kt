package com.oxygensend.notifications.context.dto

import com.oxygensend.notifications.domain.communication.PhoneCode
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

data class SmsDto(
    val body: @NotEmpty String,
    val phoneNumbers: @NotEmpty Set<@Valid PhoneDto>
) {
    data class PhoneDto(  //TODO add phoneNumber validation
        val number: @NotEmpty String,
        val code: PhoneCode,
        val systemId: String? = null
    )
}
