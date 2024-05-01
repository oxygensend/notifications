package com.oxygensend.notifications.application.queue

import com.oxygensend.notifications.domain.message.NotificationConsumer
import com.oxygensend.notifications.domain.message.NotificationPayload
import org.springframework.core.ResolvableType
import org.springframework.stereotype.Component

@Component
class NotificationConsumerRegistry(consumers: List<NotificationConsumer<*>>) {
    private val consumers: Map<Class<*>, NotificationConsumer<*>>

    init {
        this.consumers = convertToMap(consumers)
    }

    fun <T : NotificationPayload> get(payload: T): NotificationConsumer<T>? {
        return consumers[payload.javaClass] as NotificationConsumer<T>?
    }

    private fun convertToMap(consumers: List<NotificationConsumer<*>>): Map<Class<*>, NotificationConsumer<*>> {
        val map: MutableMap<Class<*>, NotificationConsumer<*>> = HashMap()
        consumers.forEach {
            val payloadClass = ResolvableType.forClass(it::class.java)
                .`as`(NotificationConsumer::class.java)
                .generics[0]
                .rawClass

            if (payloadClass != null) {
                map[payloadClass] = it
            }
        }

        return map
    }
}