package com.oxygensend.notifications.context.rest.controller

import com.oxygensend.notifications.config.SwaggerConstants.Companion.NOTIFICATION_DESCRIPTION
import com.oxygensend.notifications.config.SwaggerConstants.Companion.NOTIFICATION_NAME
import com.oxygensend.notifications.context.dto.NotificationDto
import com.oxygensend.notifications.context.rest.NotificationService
import com.oxygensend.notifications.context.rest.PagedListView
import com.oxygensend.notifications.context.rest.SortField
import com.oxygensend.notifications.domain.Channel
import com.oxygensend.notifications.domain.FindNotificationsQuery
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/notifications")
@Tag(name = NOTIFICATION_NAME, description = NOTIFICATION_DESCRIPTION)
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
        @RequestParam(defaultValue = "CREATED_AT") sort: SortField,
        @RequestParam(defaultValue = "DESC") direction: Sort.Direction
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

    @PostMapping("{id}/mark_seen")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun markAsSeen(@PathVariable id: UUID) {
        notificationService.markAsSeen(id)
    }

}