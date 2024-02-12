package com.oxygensend.notifications.domain.recipient

class Telegram(val chatId: String, val systemId: String? = null): Recipient
