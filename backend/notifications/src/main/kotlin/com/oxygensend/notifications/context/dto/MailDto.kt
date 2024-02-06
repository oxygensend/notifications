package com.oxygensend.notifications.context.dto

import com.oxygensend.notifications.domain.Mail
import jakarta.validation.constraints.NotEmpty

data class MailDto(val subject: @NotEmpty String, val body: @NotEmpty String, val emails: Set<String>) {
    fun toDomain(): Mail {
        return Mail(subject, body)
    }
}
