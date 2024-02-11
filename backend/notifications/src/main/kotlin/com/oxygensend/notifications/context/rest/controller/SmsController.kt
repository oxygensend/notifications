package com.oxygensend.notifications.context.rest.controller

import com.oxygensend.notifications.config.NotificationProfile.Companion.SMS_REST
import com.oxygensend.notifications.context.MessageCommand
import com.oxygensend.notifications.context.Messenger
import com.oxygensend.notifications.context.dto.SmsDto
import com.oxygensend.notifications.context.rest.MessagePayload
import com.oxygensend.notifications.context.rest.MessageView
import com.oxygensend.notifications.context.rest.SwaggerConstants.Companion.SMS_DESCRIPTION
import com.oxygensend.notifications.context.rest.SwaggerConstants.Companion.SMS_NAME
import com.oxygensend.notifications.domain.communication.Channel
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Profile(SMS_REST)
@RestController
@RequestMapping("/v1/notifications")
@Tag(name = SMS_NAME, description = SMS_DESCRIPTION)
internal class SmsController(private val messenger: Messenger) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/smsAsync")
    @ResponseStatus(HttpStatus.ACCEPTED)
    suspend fun smsAsync(@RequestBody messagePayload: @Valid MessagePayload<SmsDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending sms notification asynchronously for service {}", messagePayload.serviceId)
        messenger.sendAsync(MessageCommand.forSms(messagePayload), Channel.SMS)
        return ResponseEntity.ok(MessageView.ok())
    }

    @PostMapping("/smsSync")
    @ResponseStatus(HttpStatus.OK)
    fun smsSync(@RequestBody messagePayload: @Valid MessagePayload<SmsDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending sms notification synchronously for service {}", messagePayload.serviceId)
        messenger.send(MessageCommand.forSms(messagePayload), Channel.SMS)
        return ResponseEntity.ok(MessageView.ok())
    }

}