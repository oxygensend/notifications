package com.oxygensend.notifications.context.rest.controller

import com.oxygensend.notifications.context.MessageCommand
import com.oxygensend.notifications.context.Messenger
import com.oxygensend.notifications.context.config.NotificationProfile
import com.oxygensend.notifications.context.config.SwaggerConstants
import com.oxygensend.notifications.context.config.SwaggerConstants.Companion.SEND_INTERNAL_ASYNC_DESCRIPTION
import com.oxygensend.notifications.context.config.SwaggerConstants.Companion.SEND_INTERNAL_SYNC_DESCRIPTION
import com.oxygensend.notifications.context.dto.InternalMessageDto
import com.oxygensend.notifications.context.rest.MessagePayload
import com.oxygensend.notifications.context.rest.MessageView
import com.oxygensend.notifications.domain.Channel
import com.oxygensend.notifications.domain.message.InternalMessage
import com.oxygensend.notifications.domain.recipient.RecipientId
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
internal class InternalMessageController(private val messenger: Messenger) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Operation(summary = SEND_INTERNAL_ASYNC_DESCRIPTION)
    @PostMapping("/internalAsync")
    @ResponseStatus(HttpStatus.ACCEPTED)
    suspend fun mailAsync(@Validated @RequestBody messagePayload: MessagePayload<InternalMessageDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending internal notification asynchronously for service {}", messagePayload.serviceId)
        messenger.sendAsync(MessageCommand.forPayload<InternalMessage, RecipientId, InternalMessageDto>(messagePayload), Channel.INTERNAL)
        return ResponseEntity.ok(MessageView.ok())
    }


    @Operation(summary = SEND_INTERNAL_SYNC_DESCRIPTION)
    @PostMapping("/internalSync")
    @ResponseStatus(HttpStatus.OK)
    fun mailSync(@Validated @RequestBody messagePayload: MessagePayload<InternalMessageDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending internal notification synchronously for service {}", messagePayload.serviceId)
        messenger.send(MessageCommand.forPayload<InternalMessage, RecipientId, InternalMessageDto>(messagePayload), Channel.INTERNAL)
        return ResponseEntity.ok(MessageView.ok())
    }
}