package com.oxygensend.notifications.domain.channel.sms

interface SmsNotifier {
    fun notify(sms: Sms)
}