package com.oxygensend.notifications.application.rest.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

data class WhatsappDto(
    @field:NotEmpty(message = "body cannot be empty")
    val body: String?,
    @field:NotEmpty(message = "recipients cannot be empty")
    @field:Valid
    override val recipients: Set<PhoneDto>
) : MessageDto {
    data class PhoneDto(  //TODO add phoneNumber validation
        @field:NotEmpty(message = "number cannot be empty")
        val number: String?,
        val systemId: String?
    ) : RecipientDto

    override fun type(): String = "Whatsapp"
}
