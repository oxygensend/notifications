package com.oxygensend.notifications.context.rest

import com.oxygensend.notifications.config.NotificationProfile.Companion.MAIL_REST
import com.oxygensend.notifications.context.MessageCommand
import com.oxygensend.notifications.context.Messenger
import com.oxygensend.notifications.context.dto.MailDto
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
@Profile(MAIL_REST)
internal class MailController(messenger: Messenger) : NotificationController(messenger) {

    @PostMapping("/mailAsync")
    suspend fun mailAsync(@RequestBody messagePayload: @Valid MessagePayload<MailDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending mail notification asynchronously for service {}", messagePayload.serviceId)
        messenger.sendAsync(MessageCommand.forMail(messagePayload), Channel.EMAIL)
        return ResponseEntity.ok(MessageView.ok())
    }


    @PostMapping("/mailSync")
    fun mailSync(@RequestBody messagePayload: @Valid MessagePayload<MailDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending mail notification synchronously for service {}", messagePayload.serviceId)
        messenger.send(MessageCommand.forMail(messagePayload), Channel.EMAIL)
        return ResponseEntity.ok(MessageView.ok())
    }
}