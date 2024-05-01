package com.oxygensend.notifications.application.config

class NotificationProfile {
    companion object {
        const val SMS: String = "sms";
        const val MAIL: String = "mail";
        const val WHATSAPP: String = "whatsapp";
        const val INTERNAL: String = "internal"
        const val REST: String = "rest"
        const val KAFKA: String = "kafka"

        const val SMS_REST: String = "$SMS & $REST"
        const val WHATSAPP_REST: String = "$WHATSAPP & $REST"
        const val INTERNAL_REST: String = "$INTERNAL & $REST"
        const val MAIL_REST: String = "$MAIL & $REST"

        const val SMS_KAFKA: String = "$SMS & $KAFKA"
        const val WHATSAPP_KAFKA: String = "$WHATSAPP & $KAFKA"
        const val INTERNAL_KAFKA: String = "$INTERNAL & $KAFKA"
        const val MAIL_KAFKA: String = "$MAIL & $KAFKA"
    }


}