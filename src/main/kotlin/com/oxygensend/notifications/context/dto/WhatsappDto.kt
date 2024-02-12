package com.oxygensend.notifications.context.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class WhatsappDto(
    @field:NotNull @field:NotBlank val body: String,
    @field:NotEmpty @field:Valid val phoneNumbers: Set<PhoneDto>
) {
    data class PhoneDto(  //TODO add phoneNumber validation
        @field:NotNull @field:NotBlank val number: String?,
        val systemId: String?
    )
}
