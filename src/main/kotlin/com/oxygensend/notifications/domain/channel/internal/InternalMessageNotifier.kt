package com.oxygensend.notifications.domain.channel.internal

interface InternalMessageNotifier {
    fun notify(message: InternalMessage)
}