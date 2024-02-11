package com.oxygensend.notifications.context.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class MailDto(
    val subject: @NotEmpty String,
    val body: @NotEmpty String,
    val emails: @NotEmpty Set<@Valid EmailDto>
) {
    data class EmailDto(
        val address: @Email String,
        val systemId: String? = null
    )
}
