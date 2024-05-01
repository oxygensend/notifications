package com.oxygensend.notifications.application.rest.controller

import com.oxygensend.notifications.application.config.NotificationProfile
import com.oxygensend.notifications.application.config.SwaggerConstants
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.SEND_INTERNAL_ASYNC_DESCRIPTION
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.SEND_INTERNAL_SYNC_DESCRIPTION
import com.oxygensend.notifications.application.rest.dto.InternalMessageDto
import com.oxygensend.notifications.application.rest.MessageView
import com.oxygensend.notifications.application.rest.NotificationService
import com.oxygensend.notifications.application.rest.dto.RestMessagePayload
import com.oxygensend.notifications.domain.channel.internal.InternalMessage
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@Profile(NotificationProfile.INTERNAL_REST)
@RestController
@RequestMapping("/v1/notifications")
@Tag(name = SwaggerConstants.INTERNAL_NAME, description = SwaggerConstants.INTERNAL_DESCRIPTION)
internal class InternalMessageController(
    private val notificationService: NotificationService
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Operation(summary = SEND_INTERNAL_ASYNC_DESCRIPTION)
    @PostMapping("/internalAsync")
    @ResponseStatus(HttpStatus.ACCEPTED)
    suspend fun mailAsync(@Validated @RequestBody messagePayload: RestMessagePayload<InternalMessageDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending internal notification asynchronously for service {}", messagePayload.serviceId)
        notificationService.send<InternalMessageDto, InternalMessage>(messagePayload)
        return ResponseEntity.ok(MessageView.ok())
    }


    @Operation(summary = SEND_INTERNAL_SYNC_DESCRIPTION)
    @PostMapping("/internalSync")
    @ResponseStatus(HttpStatus.OK)
    fun mailSync(@Validated @RequestBody messagePayload: RestMessagePayload<InternalMessageDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending internal notification synchronously for service {}", messagePayload.serviceId)
        notificationService.sendSync<InternalMessageDto, InternalMessage>(messagePayload)
        return ResponseEntity.ok(MessageView.ok())
    }
}