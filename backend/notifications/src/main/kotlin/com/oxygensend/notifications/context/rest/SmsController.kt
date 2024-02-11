package com.oxygensend.notifications.context.rest

import com.oxygensend.notifications.config.NotificationProfile.Companion.SMS_REST
import com.oxygensend.notifications.context.MessageCommand
import com.oxygensend.notifications.context.Messenger
import com.oxygensend.notifications.context.dto.SmsDto
import com.oxygensend.notifications.domain.Channel
import jakarta.validation.Valid
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/notifications")
@Profile(SMS_REST)
internal class SmsController(messenger: Messenger) : NotificationController(messenger) {

    @PostMapping("/smsAsync")
    suspend fun smsAsync(@RequestBody messagePayload: @Valid MessagePayload<SmsDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending sms notification asynchronously for service {}", messagePayload.serviceId)
        messenger.sendAsync(MessageCommand.forSms(messagePayload), Channel.SMS)
        return ResponseEntity.ok(MessageView.ok())
    }

    @PostMapping("/smsSync")
    fun smsSync(@RequestBody messagePayload: @Valid MessagePayload<SmsDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending sms notification synchronously for service {}", messagePayload.serviceId)
        messenger.send(MessageCommand.forSms(messagePayload), Channel.SMS)
        return ResponseEntity.ok(MessageView.ok())
    }

}