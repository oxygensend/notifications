package com.oxygensend.notifications.infrastructure.queue

import com.oxygensend.notifications.application.queue.NotificationPayloadClassesProvider
import com.oxygensend.notifications.domain.message.NotificationPayload
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AssignableTypeFilter
import org.springframework.stereotype.Component

@Component
internal class SpringNotificationPayloadClassesProvider : NotificationPayloadClassesProvider {
    companion object {
        private const val PAYLOAD_PACKAGE = "com.oxygensend.notifications.domain.channel"
    }

    override fun <T : NotificationPayload> get(): Set<Class<T>> {
        val provider = ClassPathScanningCandidateComponentProvider(false)
        provider.addIncludeFilter(AssignableTypeFilter(NotificationPayload::class.java))
        return provider.findCandidateComponents(PAYLOAD_PACKAGE)
            .map { Class.forName(it.beanClassName) as Class<T> }
            .toSet()
    }
}