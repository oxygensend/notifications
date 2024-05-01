package com.oxygensend.notifications.infrastructure.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.oxygensend.notifications.application.queue.NotificationPayloadClassesProvider
import com.oxygensend.notifications.domain.message.NotificationHeaders
import com.oxygensend.notifications.domain.message.NotificationMessage
import com.oxygensend.notifications.domain.message.NotificationPayload
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.stereotype.Component

@Component
internal class NotificationMessageKafkaFactory(
    notificationPayloadClassProvider: NotificationPayloadClassesProvider,
    private val objectMapper: ObjectMapper
) {

    private val supportedPayloadClasses: Set<Class<NotificationPayload>> = notificationPayloadClassProvider.get()

    fun <T : NotificationPayload> create(record: ConsumerRecord<String, String>): NotificationMessage<T> {
        val headers = createHeaders(record)
        val payload: T = createPayload(record.value(), headers)
        return NotificationMessage(headers, payload)
    }

    private fun createHeaders(record: ConsumerRecord<String, String>): NotificationHeaders {
        val kafkaHeaders = record.headers();
        val id = record.key();
        val type = kafkaHeaders.lastHeader("type").value().toString(Charsets.UTF_8)
        val login = kafkaHeaders.lastHeader("login")?.value()?.toString(Charsets.UTF_8)
        val requestId = kafkaHeaders.lastHeader("requestId")?.value()?.toString(Charsets.UTF_8)
        val serviceId = kafkaHeaders.lastHeader("serviceId").value().toString(Charsets.UTF_8)
        return NotificationHeaders(
            id = id,
            type = type,
            login = login,
            requestId = requestId,
            serviceId = serviceId
        )
    }

    private fun <T : NotificationPayload> createPayload(value: String, headers: NotificationHeaders): T {
        val payloadClass: Class<T> = findPayloadClass(headers.type)
        try {
            return objectMapper.readValue(value, payloadClass)
        } catch (e: Exception) {
            throw IllegalArgumentException("Cannot deserialize payload", e)
        }
    }

    private fun <T : NotificationPayload> findPayloadClass(type: String): Class<T> {
        return supportedPayloadClasses.firstOrNull { it.simpleName == type } as Class<T>?
            ?: throw IllegalArgumentException("Unsupported payload type: $type")
    }


}