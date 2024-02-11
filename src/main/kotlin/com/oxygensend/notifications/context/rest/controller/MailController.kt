package com.oxygensend.notifications.context.rest.controller

import com.oxygensend.notifications.config.NotificationProfile.Companion.MAIL_REST
import com.oxygensend.notifications.context.MessageCommand
import com.oxygensend.notifications.context.Messenger
import com.oxygensend.notifications.context.dto.MailDto
import com.oxygensend.notifications.context.rest.MessagePayload
import com.oxygensend.notifications.context.rest.MessageView
import com.oxygensend.notifications.context.rest.SwaggerConstants.Companion.MAIL_DESCRIPTION
import com.oxygensend.notifications.context.rest.SwaggerConstants.Companion.MAIL_NAME
import com.oxygensend.notifications.domain.communication.Channel
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
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

    @PostMapping("/mailAsync")
    @ResponseStatus(HttpStatus.ACCEPTED)
    suspend fun mailAsync(@Validated @RequestBody messagePayload: MessagePayload<MailDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending mail notification asynchronously for service {}", messagePayload.serviceId)
        messenger.sendAsync(MessageCommand.forMail(messagePayload), Channel.EMAIL)
        return ResponseEntity.ok(MessageView.ok())
    }


    @PostMapping("/mailSync")
    @ResponseStatus(HttpStatus.OK)
    fun mailSync(@Validated @RequestBody messagePayload: MessagePayload<MailDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending mail notification synchronously for service {}", messagePayload.serviceId)
        messenger.send(MessageCommand.forMail(messagePayload), Channel.EMAIL)
        return ResponseEntity.ok(MessageView.ok())
    }
}