package com.oxygensend.notifications.context.rest;


import com.oxygensend.notifications.context.MessageCommand;
import com.oxygensend.notifications.context.Messenger;
import com.oxygensend.notifications.domain.Channel;
import com.oxygensend.notifications.context.dto.MailDto;
import com.oxygensend.notifications.context.dto.SmsDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/notifications")
class NotificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);
    private final Messenger messenger;

    public NotificationController(Messenger messenger) {
        this.messenger = messenger;
    }

    @PostMapping("/mailAsync")
    public ResponseEntity<MessageView> mailAsync(@RequestBody @Valid MessagePayload<MailDto> messagePayload) {
        LOGGER.info("REST: Sending mail notification asynchronously for service {}", messagePayload.serviceID());
        messenger.sendAsync(MessageCommand.forMail(messagePayload), Channel.EMAIL);
        return ResponseEntity.ok(MessageView.ok());
    }

    @PostMapping("/smsAsync")
    public ResponseEntity<MessageView> smsAsync(@RequestBody @Valid MessagePayload<SmsDto> messagePayload) {
        LOGGER.info("REST: Sending sms notification asynchronously for service {}", messagePayload.serviceID());
        messenger.sendAsync(MessageCommand.forSms(messagePayload), Channel.SMS);
        return ResponseEntity.ok(MessageView.ok());
    }


    @PostMapping("/mailSync")
    public ResponseEntity<MessageView> mailSync(@RequestBody @Valid MessagePayload<MailDto> messagePayload) {
        LOGGER.info("REST: Sending mail notification synchronously for service {}", messagePayload.serviceID());
        messenger.send(MessageCommand.forMail(messagePayload), Channel.EMAIL);
        return ResponseEntity.ok(MessageView.ok());
    }

    @PostMapping("/smsSync")
    public ResponseEntity<MessageView> smsSync(@RequestBody @Valid MessagePayload<SmsDto> messagePayload) {
        LOGGER.info("REST: Sending sms notification synchronously for service {}", messagePayload.serviceID());
        messenger.send(MessageCommand.forSms(messagePayload), Channel.SMS);
        return ResponseEntity.ok(MessageView.ok());
    }

 }