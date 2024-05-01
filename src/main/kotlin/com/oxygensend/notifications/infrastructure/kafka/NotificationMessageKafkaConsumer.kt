package com.oxygensend.notifications.infrastructure.kafka

import com.oxygensend.notifications.domain.message.NotificationMessageProcessor
import com.oxygensend.notifications.domain.message.NotificationPayload
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
internal class NotificationMessageKafkaConsumer(
    private val factory: NotificationMessageKafkaFactory,
    private val processor: NotificationMessageProcessor
) : AcknowledgingMessageListener<String, String> {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(NotificationMessageKafkaConsumer::class.java)
    }

    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        LOGGER.info("Received message. Key: ${data.key()}, Partition: ${data.partition()}, Offset: ${data.offset()}")
        acknowledge(acknowledgment);
        process(data)
    }

    private fun acknowledge(acknowledgment: Acknowledgment?) {
        acknowledgment?.acknowledge()
    }

    private fun process(data: ConsumerRecord<String, String>) {
        val message = factory.create<NotificationPayload>(data)
        processor.process(message)
    }

}