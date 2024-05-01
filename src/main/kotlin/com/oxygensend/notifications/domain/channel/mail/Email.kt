package com.oxygensend.notifications.domain.channel.mail

import com.oxygensend.notifications.domain.message.Recipient

data class Email(val email: String, val systemId: String? = null): Recipient
