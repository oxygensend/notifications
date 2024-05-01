package com.oxygensend.notifications.application.rest.controller

import com.oxygensend.commons_jdk.PagedListView
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.GET_NOTIFICATIONS_DESCRIPTION
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.MARK_AS_SEEN_DESCRIPTION
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.NOTIFICATION_DESCRIPTION
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.NOTIFICATION_NAME
import com.oxygensend.notifications.application.rest.dto.NotificationDto
import com.oxygensend.notifications.application.rest.NotificationService
import com.oxygensend.notifications.domain.history.part.SortField
import com.oxygensend.notifications.domain.history.part.Channel
import com.oxygensend.notifications.domain.history.part.FindNotificationsQuery
import io.swagger.v3.oas.annotations.Operation
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

    @Operation(summary = GET_NOTIFICATIONS_DESCRIPTION)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getNotifications(
        pageable: Pageable,
        @RequestParam(required = false, name = "search") search: String?,
        @RequestParam(required = false, name = "recipient") recipient: String?,
        @RequestParam(required = false, name = "recipient_id") recipientId: String?,
        @RequestParam(required = false, name = "channel") channel: Channel?,
        @RequestParam(required = false, name = "service_id") serviceId: String?,
        @RequestParam(required = false, name = "request_id") requestId: String?,
        @RequestParam(defaultValue = "CREATED_AT", name = "sort") sort: SortField,
        @RequestParam(defaultValue = "DESC", name = "direction") direction: Sort.Direction
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

    @Operation(summary = MARK_AS_SEEN_DESCRIPTION)
    @PostMapping("{id}/mark_seen")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun markAsSeen(@PathVariable id: UUID) {
        notificationService.markAsSeen(id)
    }

}