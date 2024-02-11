package com.oxygensend.notifications.context.rest.controller

import com.oxygensend.notifications.context.dto.NotificationDto
import com.oxygensend.notifications.context.rest.NotificationService
import com.oxygensend.notifications.context.rest.PagedListView
import com.oxygensend.notifications.context.rest.SortField
import com.oxygensend.notifications.domain.FindNotificationsQuery
import com.oxygensend.notifications.domain.communication.Channel
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/notifications")
internal class NotificationController(private val notificationService: NotificationService) {


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getNotifications(
        pageable: Pageable,
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) recipient: String?,
        @RequestParam(required = false) recipientId: String?,
        @RequestParam(required = false) channel: Channel?,
        @RequestParam(required = false) serviceId: String?,
        @RequestParam(required = false) requestId: String?,
        @RequestParam(required = false) sort: SortField = SortField.CREATED_AT,
        @RequestParam(required = false) direction: Sort.Direction = Sort.Direction.DESC,
    ): PagedListView<NotificationDto> {
        val query = FindNotificationsQuery(
            pageable = pageable,
            search = search,
            recipient = recipient,
            recipientId = recipientId,
            channel = channel,
            serviceId = serviceId,
            requestId = requestId,
            sort = Sort.by(direction, sort.value)
        )
        return notificationService.findAllPaginated(query)
    }
}