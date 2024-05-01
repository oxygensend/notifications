package com.oxygensend.notifications.application.rest.dto

import com.oxygensend.commons_jdk.validation.ValidEmail
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class MailDto(
    @field:NotEmpty(message = "subject cannot be empty")
    val subject: String?,
    @field:NotEmpty(message = "body cannot be empty")
    val body: String?,
    @field:NotEmpty(message = "recipients cannot be empty")
    @field:Valid
    override val recipients: Set<EmailDto>
) : MessageDto {
    data class EmailDto(
        @field:NotNull @field:ValidEmail val address: String?,
        val systemId: String? = null
    ) : RecipientDto

    override fun type(): String = "Mail"

}
