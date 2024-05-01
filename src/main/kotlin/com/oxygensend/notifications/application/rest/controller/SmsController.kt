package com.oxygensend.notifications.application.rest.controller

import com.oxygensend.notifications.application.config.NotificationProfile.Companion.SMS_REST
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.SEND_SMS_ASYNC_DESCRIPTION
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.SEND_SMS_SYNC_DESCRIPTION
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.SMS_DESCRIPTION
import com.oxygensend.notifications.application.config.SwaggerConstants.Companion.SMS_NAME
import com.oxygensend.notifications.application.rest.dto.SmsDto
import com.oxygensend.notifications.application.rest.MessageView
import com.oxygensend.notifications.application.rest.NotificationService
import com.oxygensend.notifications.application.rest.dto.RestMessagePayload
import com.oxygensend.notifications.domain.channel.sms.Sms
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Profile(SMS_REST)
@RestController
@RequestMapping("/v1/notifications")
@Tag(name = SMS_NAME, description = SMS_DESCRIPTION)
internal class SmsController(
    private val notificationService: NotificationService
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Operation(summary = SEND_SMS_ASYNC_DESCRIPTION)
    @PostMapping("/smsAsync")
    @ResponseStatus(HttpStatus.ACCEPTED)
    suspend fun smsAsync(@Validated @RequestBody messagePayload: RestMessagePayload<SmsDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending sms notification asynchronously for service {}", messagePayload.serviceId)
        notificationService.send<SmsDto, Sms>(messagePayload)
        return ResponseEntity.ok(MessageView.ok())
    }

    @Operation(summary = SEND_SMS_SYNC_DESCRIPTION)
    @PostMapping("/smsSync")
    @ResponseStatus(HttpStatus.OK)
    fun smsSync(@Validated @RequestBody messagePayload: @Valid RestMessagePayload<SmsDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending sms notification synchronously for service {}", messagePayload.serviceId)
        notificationService.sendSync<SmsDto, Sms>(messagePayload)
        return ResponseEntity.ok(MessageView.ok())
    }

}