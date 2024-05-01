package com.oxygensend.notifications.infrastructure.queue

import com.oxygensend.notifications.application.config.NotificationsProperties
import com.oxygensend.notifications.domain.history.NotificationHistoryRepository
import com.oxygensend.notifications.domain.history.NotificationRepository
import com.oxygensend.notifications.domain.message.NotificationMessage
import com.oxygensend.notifications.domain.message.NotificationPayload
import com.oxygensend.notifications.domain.message.Progress
import com.oxygensend.notifications.domain.service.DomainFactory
import org.springframework.stereotype.Component

@Component
internal class NotificationHistoryRepositoryImpl(
    private val notificationRepository: NotificationRepository,
    private val notificationsProperties: NotificationsProperties
) : NotificationHistoryRepository {

    override fun <T : NotificationPayload> save(notificationMessage: NotificationMessage<T>, progress: Progress) {
        if (!isHistoryEnabled()) {
            return
        }

        progress.value.entries.map {
            val notificationProgress = it.value;
            DomainFactory.createNotification(notificationProgress.notificationId, notificationMessage, it.key, notificationProgress.status)
        }.apply { notificationRepository.saveAll(this) }
    }

    private fun isHistoryEnabled(): Boolean = notificationsProperties.storeInDatabase

}