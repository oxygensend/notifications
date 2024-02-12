package com.oxygensend.notifications.context.dto

import com.oxygensend.notifications.domain.recipient.PhoneCode
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class SmsDto(
    @field:NotNull @field:NotBlank val body: String?,
    @field:NotEmpty @field:Valid override val recipients: Set<PhoneDto>
): MessageDto {
    data class PhoneDto(  //TODO add phoneNumber validation
        @field:NotNull @field:NotBlank val number: String?,
        @field:NotNull val code: PhoneCode?,
        val systemId: String?
    ): RecipientDto
}
