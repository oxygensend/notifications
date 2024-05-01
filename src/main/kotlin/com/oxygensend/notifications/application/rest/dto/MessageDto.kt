package com.oxygensend.notifications.application.rest.dto

interface MessageDto {
    val recipients: Set<RecipientDto>

    fun type(): String
}