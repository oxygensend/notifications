package com.oxygensend.notifications.context.dto

import jakarta.validation.constraints.NotEmpty

data class MailDto(val subject: @NotEmpty String, val body: @NotEmpty String, val emails: Set<EmailDto>) {
    data class EmailDto(val address: String, val systemId: String? = null)
}
