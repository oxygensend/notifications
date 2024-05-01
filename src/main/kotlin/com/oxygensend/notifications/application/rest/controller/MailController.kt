package com.oxygensend.notifications.application.rest.controller

import com.oxygensend.notifications.application.config.NotificationProfile.Companion.MAIL_REST
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.MAIL_DESCRIPTION
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.MAIL_NAME
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.SEND_MAIL_ASYNC_DESCRIPTION
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.SEND_MAIL_SYNC_DESCRIPTION
import com.oxygensend.notifications.application.rest.dto.MailDto
import com.oxygensend.notifications.application.rest.MessageView
import com.oxygensend.notifications.application.rest.NotificationService
import com.oxygensend.notifications.application.rest.dto.RestMessagePayload
import com.oxygensend.notifications.domain.channel.mail.Mail
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
internal class MailController(
    private val notificationService: NotificationService
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Operation(summary = SEND_MAIL_ASYNC_DESCRIPTION)
    @PostMapping("/mailAsync")
    @ResponseStatus(HttpStatus.ACCEPTED)
    suspend fun mailAsync(@Validated @RequestBody messagePayload: RestMessagePayload<MailDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending mail notification asynchronously for service {}", messagePayload.serviceId)
        notificationService.send<MailDto, Mail>(messagePayload)
        return ResponseEntity.ok(MessageView.ok())
    }


    @Operation(summary = SEND_MAIL_SYNC_DESCRIPTION)
    @PostMapping("/mailSync")
    @ResponseStatus(HttpStatus.OK)
    fun mailSync(@Validated @RequestBody messagePayload: RestMessagePayload<MailDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending mail notification synchronously for service {}", messagePayload.serviceId)
        notificationService.sendSync<MailDto, Mail>(messagePayload)
        return ResponseEntity.ok(MessageView.ok())
    }
}