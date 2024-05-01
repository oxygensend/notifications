package com.oxygensend.notifications.domain.channel.whatsapp

import com.oxygensend.notifications.domain.message.Recipient

data class WhatsappPhone(val phone: String, val systemId: String? = null): Recipient