package com.oxygensend.notifications.application.rest

import com.oxygensend.notifications.application.rest.dto.MessageDto
import com.oxygensend.notifications.application.rest.dto.NotificationDto
import com.oxygensend.notifications.application.rest.dto.RestMessagePayload
import com.oxygensend.notifications.domain.exception.NotificationNotFoundException
import com.oxygensend.notifications.domain.history.NotificationRepository
import com.oxygensend.notifications.domain.history.part.FindNotificationsQuery
import com.oxygensend.notifications.domain.message.NotificationMessageProcessor
import com.oxygensend.notifications.domain.message.NotificationPayload
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class NotificationService(
    private val repository: NotificationRepository,
    private val factory: NotificationMessageRestFactory,
    private val processor: NotificationMessageProcessor
) {

    fun findAllPaginated(query: FindNotificationsQuery): PagedListView<NotificationDto> {
        val paginator = repository.findAll(query)
        val data = paginator.map { NotificationDto.fromDomain(it) }.toList();

        return PagedListView(data, paginator.totalElements, paginator.number + 1, paginator.totalPages)
    }

    fun markAsSeen(id: UUID) {
        val notification = repository.findById(id).orElseThrow { throw NotificationNotFoundException("Notification with $id not found") }
        notification.markAsSeen().let { repository.save(it) }
    }

    fun <T : MessageDto, R : NotificationPayload> send(messagePayload: RestMessagePayload<T>) {
        val message = factory.create<R, T>(messagePayload)
        processor.process(message)
    }

    fun <T : MessageDto, R : NotificationPayload> sendSync(messagePayload: RestMessagePayload<T>) {
        val message = factory.create<R, T>(messagePayload)
        processor.processSynchronously(message)
    }

    fun delete(id: UUID) {
        val notification = repository.findById(id).orElseThrow { throw NotificationNotFoundException("Notification with $id not found") }
        if (notification.deleted) throw NotificationNotFoundException("Notification with $id already deleted")
        notification.delete().let { repository.save(it) }
    }
}