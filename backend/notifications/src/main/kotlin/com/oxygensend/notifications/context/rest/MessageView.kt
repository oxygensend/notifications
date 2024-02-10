package com.oxygensend.notifications.context.rest


internal data class MessageView(val status: String) {
    companion object {
        fun ok(): MessageView {
            return MessageView("ok")
        }
    }
}
