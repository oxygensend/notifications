package com.oxygensend.notifications.context.rest

import com.oxygensend.commons_jdk.PagedListView
import com.oxygensend.notifications.context.dto.NotificationDto
import com.oxygensend.notifications.domain.FindNotificationsQuery
import com.oxygensend.notifications.domain.NotificationRepository
import com.oxygensend.notifications.domain.exception.NotificationNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class NotificationService(private val repository: NotificationRepository) {

    fun findAllPaginated(query: FindNotificationsQuery): PagedListView<NotificationDto> {
        val paginator = repository.findAll(query)
        val data = paginator.map { NotificationDto.fromDomain(it) }.toList();

        return PagedListView(data, paginator.numberOfElements, paginator.number, paginator.totalPages)
    }

    fun markAsSeen(id: UUID) {
        val notification = repository.findById(id).orElseThrow { throw NotificationNotFoundException("Notification with $id not found") }
        notification.markAsSeen().let { repository.save(it) }
    }
}