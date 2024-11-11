package com.oxygensend.notifications.infrastructure.queue

import com.oxygensend.notifications.application.authentication.AuthService
import com.oxygensend.notifications.application.queue.NotificationConsumerRegistry
import com.oxygensend.notifications.application.queue.QueueExecutor
import com.oxygensend.notifications.domain.message.NotificationMessage
import com.oxygensend.notifications.domain.message.NotificationMessageProcessor
import com.oxygensend.notifications.domain.message.NotificationPayload
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
internal class DomainNotificationMessageProcessor(
    private val consumerRegistry: NotificationConsumerRegistry,
    private val authService: AuthService,
    private val queueExecutor: QueueExecutor
) : NotificationMessageProcessor {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(this::class.java)
    }

    override fun <T : NotificationPayload> process(message: NotificationMessage<T>) {
        val consumer = consumerRegistry.get(message.payload)
        if (consumer == null) {
            LOGGER.info("Undefined consumer for: {}", message.headers.type)
            return
        }

        queueExecutor.execute(message.headers.id) {
            authService.authenticate(message.headers)
            consumer.consumeSafely(message);
        }
    }

    override fun <T : NotificationPayload> processSynchronously(message: NotificationMessage<T>) {
        val consumer = consumerRegistry.get(message.payload)
        if (consumer == null) {
            LOGGER.info("Undefined consumer for: {}", message.headers.type)
            return
        }

        authService.authenticate(message.headers)
        consumer.consumeSafely(message);
    }
}