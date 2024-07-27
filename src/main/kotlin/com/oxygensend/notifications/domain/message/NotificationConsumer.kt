package com.oxygensend.notifications.domain.message

import org.slf4j.LoggerFactory

interface NotificationConsumer<T : NotificationPayload> {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(this::class.java)
    }


    fun consume(message: NotificationMessage<T>)


    fun consumeSafely(message: NotificationMessage<T>) {
        val loggingContext = loggingContext(message)

        LOGGER.info("Consuming message: {} ", loggingContext)

        try {
            consume(message)
        } catch (ex: Exception) {
            LOGGER.error("Error: {}", loggingContext, ex)
        }

    }

    private fun loggingContext(message: NotificationMessage<T>): String {
        return "[id=${message.headers.id}, type=${message.headers.type}, consumer=${this::class}, recipients=${message.payload.recipients}"
    }
}

