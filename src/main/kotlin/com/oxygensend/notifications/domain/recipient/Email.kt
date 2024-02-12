package com.oxygensend.notifications.domain.recipient

data class Email(val address: String, val systemId: String? = null): Recipient
