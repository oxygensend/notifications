package com.oxygensend.notifications.context.rest

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import java.time.LocalDateTime

data class MessagePayload<C>(
    val content: @Valid C,
    val login: String?,
    val serviceId: @NotEmpty String,
    val requestId: String?,
    val createdAt: LocalDateTime?
)
