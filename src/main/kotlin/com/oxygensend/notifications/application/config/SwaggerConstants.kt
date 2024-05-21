package com.oxygensend.notifications.application.config

class SwaggerConstants {
    companion object {
        const val NOTIFICATION_NAME = "Notification"
        const val NOTIFICATION_DESCRIPTION = "Notification operations"
        const val GET_NOTIFICATIONS_DESCRIPTION = "Get notifications"
        const val MARK_AS_SEEN_DESCRIPTION = "Mark notification as seen"
        const val DELETE_NOTIFICATION_DESCRIPTION = "Delete notification"

        const val MAIL_NAME = "Mail"
        const val MAIL_DESCRIPTION = "Mail notification"
        const val SEND_MAIL_ASYNC_DESCRIPTION = "Send mail asynchronously"
        const val SEND_MAIL_SYNC_DESCRIPTION = "Send mail asynchronously"

        const val SMS_NAME = "Sms"
        const val SMS_DESCRIPTION = "Sms notification"
        const val SEND_SMS_ASYNC_DESCRIPTION = "Send sms asynchronously"
        const val SEND_SMS_SYNC_DESCRIPTION = "Send sms synchronously"

        const val WHATSAPP_NAME = "Whatsapp"
        const val WHATSAPP_DESCRIPTION = "Whatsapp notification"
        const val SEND_WHATSAPP_ASYNC_DESCRIPTION = "Send whatsapp asynchronously"
        const val SEND_WHATSAPP_SYNC_DESCRIPTION = "Send whatsapp synchronously"

        const val INTERNAL_NAME = "Internal"
        const val INTERNAL_DESCRIPTION = "Internal notification"
        const val SEND_INTERNAL_ASYNC_DESCRIPTION = "Send internal asynchronously"
        const val SEND_INTERNAL_SYNC_DESCRIPTION = "Send internal synchronously"
    }
}