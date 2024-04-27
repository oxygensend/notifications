package com.oxygensend.notifications.context.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

data class InternalMessageDto(
    @field:NotEmpty(message = "content cannot be empty")
    val content: String?,
    @field:NotEmpty(message = "recipients cannot be empty")
    @field:Valid
    override val recipients: Set<RecipientIdDto>

) : MessageDto {
    data class RecipientIdDto(
        @field:NotEmpty(message = "id cannot be empty")
        val id: String? = null
    ) : RecipientDto
}