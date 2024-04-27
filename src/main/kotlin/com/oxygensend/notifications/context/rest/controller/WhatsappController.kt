package com.oxygensend.notifications.context.rest.controller

import com.oxygensend.notifications.context.MessageCommand
import com.oxygensend.notifications.context.Messenger
import com.oxygensend.notifications.context.config.NotificationProfile.Companion.WHATSAPP_REST
import com.oxygensend.notifications.context.config.SwaggerConstants.Companion.SEND_WHATSAPP_ASYNC_DESCRIPTION
import com.oxygensend.notifications.context.config.SwaggerConstants.Companion.SEND_WHATSAPP_SYNC_DESCRIPTION
import com.oxygensend.notifications.context.config.SwaggerConstants.Companion.WHATSAPP_DESCRIPTION
import com.oxygensend.notifications.context.config.SwaggerConstants.Companion.WHATSAPP_NAME
import com.oxygensend.notifications.context.dto.WhatsappDto
import com.oxygensend.notifications.context.rest.MessagePayload
import com.oxygensend.notifications.context.rest.MessageView
import com.oxygensend.notifications.domain.Channel
import com.oxygensend.notifications.domain.message.Sms
import com.oxygensend.notifications.domain.recipient.WhatsappPhone
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/notifications")
@Profile(WHATSAPP_REST)
@Tag(name = WHATSAPP_NAME, description = WHATSAPP_DESCRIPTION)
internal class WhatsappController(private val messenger: Messenger) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Operation(summary = SEND_WHATSAPP_ASYNC_DESCRIPTION)
    @PostMapping("/whatsappAsync")
    @ResponseStatus(HttpStatus.ACCEPTED)
    suspend fun mailAsync(@Validated @RequestBody messagePayload: MessagePayload<WhatsappDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending whatsapp notification asynchronously for service {}", messagePayload.serviceId)
        messenger.sendAsync(MessageCommand.forPayload<WhatsappPhone, Sms, WhatsappDto>(messagePayload), Channel.EMAIL)
        return ResponseEntity.ok(MessageView.ok())
    }


    @Operation(summary = SEND_WHATSAPP_SYNC_DESCRIPTION)
    @PostMapping("/whatsappSync")
    @ResponseStatus(HttpStatus.OK)
    fun mailSync(@Validated @RequestBody messagePayload: MessagePayload<WhatsappDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending whatsapp notification synchronously for service {}", messagePayload.serviceId)
        messenger.send(MessageCommand.forPayload<WhatsappPhone, Sms, WhatsappDto>(messagePayload), Channel.EMAIL)
        return ResponseEntity.ok(MessageView.ok())
    }
}
