package com.oxygensend.notifications.context.dto

import com.oxygensend.notifications.domain.communication.PhoneCode
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class SmsDto(
    @field:NotNull @field:NotBlank val body: String,
    @field:NotEmpty @field:Valid val phoneNumbers: Set<PhoneDto>
) {
    data class PhoneDto(  //TODO add phoneNumber validation
        @field:NotNull @field:NotBlank val number: String?,
        @field:NotNull val code: PhoneCode?,
        val systemId: String?
    )
}
