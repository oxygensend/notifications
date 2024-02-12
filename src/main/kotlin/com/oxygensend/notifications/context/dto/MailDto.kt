package com.oxygensend.notifications.context.dto

import com.oxygensend.commons_jdk.validation.ValidEmail
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class MailDto(
    @field:NotNull @field:NotBlank val subject: String?,
    @field:NotNull @field:NotBlank val body: String?,
    @field:NotEmpty @field:Valid override val recipients: Set<EmailDto>
) : MessageDto {
    data class EmailDto(
        @field:NotNull @field:ValidEmail val address: String?,
        val systemId: String? = null
    ) : RecipientDto
}
