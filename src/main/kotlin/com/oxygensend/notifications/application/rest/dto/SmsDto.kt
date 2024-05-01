package com.oxygensend.notifications.application.rest.dto

import com.oxygensend.notifications.domain.channel.sms.PhoneCode
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class SmsDto(
    @field:NotEmpty(message = "body cannot be empty")
    val body: String?,
    @field:NotEmpty(message = "recipients cannot be empty")
    @field:Valid
    override val recipients: Set<PhoneDto>
) : MessageDto {
    data class PhoneDto(  //TODO add phoneNumber validation
        @field:NotEmpty(message = "number cannot be empty")
        val number: String?,
        @field:NotNull(message = "code cannot be null")
        val code: PhoneCode?,
        val systemId: String?
    ) : RecipientDto

    override fun type(): String = "Sms"
}
