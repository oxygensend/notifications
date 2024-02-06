package com.oxygensend.notifications.context.rest

import com.oxygensend.notifications.context.MessageCommand
import com.oxygensend.notifications.context.Messenger
import com.oxygensend.notifications.context.dto.MailDto
import com.oxygensend.notifications.context.dto.SmsDto
import com.oxygensend.notifications.domain.Channel
import jakarta.validation.Valid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/notifications")
internal class NotificationController(private val messenger: Messenger, private val applicationScope: CoroutineScope) {

    private val logger = LoggerFactory.getLogger(NotificationController::class.java);

    @PostMapping("/mailAsync")
    suspend fun mailAsync(@RequestBody messagePayload: @Valid MessagePayload<MailDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending mail notification asynchronously for service {}", messagePayload.serviceID)
        messenger.sendAsync(MessageCommand.forMail(messagePayload), Channel.EMAIL)
        return ResponseEntity.ok(MessageView.ok())
    }

    @PostMapping("/smsAsync")
    suspend fun smsAsync(@RequestBody messagePayload: @Valid MessagePayload<SmsDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending sms notification asynchronously for service {}", messagePayload.serviceID)
        messenger.sendAsync(MessageCommand.forSms(messagePayload), Channel.SMS)
        return ResponseEntity.ok(MessageView.ok())
    }

    @PostMapping("/mailSync")
    fun mailSync(@RequestBody messagePayload: @Valid MessagePayload<MailDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending mail notification synchronously for service {}", messagePayload.serviceID)
        messenger.send(MessageCommand.forMail(messagePayload), Channel.EMAIL)
        return ResponseEntity.ok(MessageView.ok())
    }

    @PostMapping("/smsSync")
    fun smsSync(@RequestBody messagePayload: @Valid MessagePayload<SmsDto>): ResponseEntity<MessageView> {
        logger.info("REST: Sending sms notification synchronously for service {}", messagePayload.serviceID)
        messenger.send(MessageCommand.forSms(messagePayload), Channel.SMS)
        return ResponseEntity.ok(MessageView.ok())
    }

}