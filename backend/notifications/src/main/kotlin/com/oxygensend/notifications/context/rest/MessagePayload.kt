package com.oxygensend.notifications.context.rest

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

data class MessagePayload<C>(
    val content: @Valid C,
    val login: String?,
    val serviceID: @NotEmpty String,
    val createdAt: String?
)
