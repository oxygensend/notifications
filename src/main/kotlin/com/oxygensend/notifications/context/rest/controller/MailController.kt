package com.oxygensend.notifications.context.rest.controller

import com.oxygensend.notifications.context.MessageCommand
import com.oxygensend.notifications.context.Messenger
import com.oxygensend.notifications.context.config.NotificationProfile.Companion.MAIL_REST
import com.oxygensend.notifications.context.config.SwaggerConstants.Companion.MAIL_DESCRIPTION
import com.oxygensend.notifications.context.config.SwaggerConstants.Companion.MAIL_NAME
import com.oxygensend.notifications.context.config.SwaggerConstants.Companion.SEND_MAIL_ASYNC_DESCRIPTION
import com.oxygensend.notifications.context.config.SwaggerConstants.Companion.SEND_MAIL_SYNC_DESCRIPTION
import com.oxygensend.notifications.context.dto.MailDto
import com.oxygensend.notifications.context.rest.MessagePayload
import com.oxygensend.notifications.context.rest.MessageView
import com.oxygensend.notifications.domain.Channel
import com.oxygensend.notifications.domain.message.Mail
import com.oxygensend.notifications.domain.recipient.Email
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@Profile(MAIL_REST)
@RestController
@RequestMapping("/v1/notifications")
@Tag(name = MAIL_NAME, description = MAIL_DESCRIPTION)
internal class MailController(private val messenger: Messenger) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Operation(summary = SEND_MAIL_ASYNC_DESCRIPTION)
    @PostMapping("/mailAsync")
    @ResponseStatus(HttpStatus.ACCEPTED)
    suspend fun mailAsync(@Validated @RequestBody messagePayload: MessagePayload<MailDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending mail notification asynchronously for service {}", messagePayload.serviceId)
        messenger.sendAsync(MessageCommand.forPayload<Email, Mail, MailDto>(messagePayload), Channel.EMAIL)
        return ResponseEntity.ok(MessageView.ok())
    }


    @Operation(summary = SEND_MAIL_SYNC_DESCRIPTION)
    @PostMapping("/mailSync")
    @ResponseStatus(HttpStatus.OK)
    fun mailSync(@Validated @RequestBody messagePayload: MessagePayload<MailDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending mail notification synchronously for service {}", messagePayload.serviceId)
        messenger.send(MessageCommand.forPayload<Email, Mail, MailDto>(messagePayload), Channel.EMAIL)
        return ResponseEntity.ok(MessageView.ok())
    }
}