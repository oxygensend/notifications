package com.oxygensend.notifications.context.rest

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import java.time.LocalDateTime

data class MessagePayload<C>(
    val content: @Valid C,
    val login: String?,
    val serviceId: @NotEmpty String,
    val requestId: String?,
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime?
)
