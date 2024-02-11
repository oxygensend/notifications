package com.oxygensend.notifications.context.rest

import com.oxygensend.notifications.context.dto.NotificationDto
import com.oxygensend.notifications.domain.FindNotificationsQuery
import com.oxygensend.notifications.domain.NotificationRepository
import org.springframework.stereotype.Service

@Service
internal class NotificationService(private val repository: NotificationRepository) {

    fun findAllPaginated(query: FindNotificationsQuery): PagedListView<NotificationDto> {
        val paginator = repository.findAll(query)
        val data = paginator.map { NotificationDto.fromDomain(it) }.toList();

        return PagedListView(data, paginator.numberOfElements, paginator.number, paginator.totalPages)
    }
}