package com.oxygensend.notifications.application.rest.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.Instant

internal data class RestMessagePayload<C>(
    val id: String?,
    @field:Valid val content: C,
    val login: String?,
    @field:NotNull @field:NotBlank val serviceId: String?,
    val requestId: String?,
    val createdAt: Instant?
)
