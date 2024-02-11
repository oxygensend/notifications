package com.oxygensend.notifications.context.rest

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class MessagePayload<C>(
    @field:Valid val content: C,
    val login: String?,
    @field:NotNull @field:NotBlank val serviceId: String?,
    val requestId: String?,
    val createdAt: LocalDateTime?
)
