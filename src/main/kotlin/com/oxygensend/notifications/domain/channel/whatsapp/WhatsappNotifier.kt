package com.oxygensend.notifications.domain.channel.whatsapp

interface WhatsappNotifier {
    fun notify(whatsapp: Whatsapp)
}