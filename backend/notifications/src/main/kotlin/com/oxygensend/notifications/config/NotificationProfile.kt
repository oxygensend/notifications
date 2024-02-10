package com.oxygensend.notifications.config

class NotificationProfile {
    companion object {
        const val SMS: String = "sms";
        const val MAIL: String = "mail";
        const val WHATSAPP: String = "whatsapp";
        const val TELEGRAM: String = "telegram";
        const val REST: String = "rest"
        const val KAFKA: String = "kafka"

        const val SMS_REST: String = "$SMS & $REST"
        const val WHATSAPP_REST: String = "$WHATSAPP & $REST"
        const val TELEGRAM_REST: String = "$TELEGRAM & $REST"
        const val MAIL_REST: String = "$MAIL & $REST"
    }


}