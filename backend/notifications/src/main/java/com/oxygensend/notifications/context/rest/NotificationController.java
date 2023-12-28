package com.oxygensend.notifications.context.rest;


import com.oxygensend.notifications.context.Messenger;
import com.oxygensend.notifications.domain.Channel;
import com.oxygensend.notifications.domain.Mail;
import com.oxygensend.notifications.domain.Sms;
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


    @PostMapping("/mailSync")
    public ResponseEntity<MessageView> mailSync(@RequestBody @Valid MessagePayload<Mail> messagePayload) {
        LOGGER.info("REST: Sending mail notification synchronously for service {}", messagePayload.serviceID());
        messenger.send(messagePayload.toCommand(), Channel.EMAIL);
        return ResponseEntity.ok(MessageView.ok());
    }

    @PostMapping("/smsSync")
    public ResponseEntity<MessageView> smsSync(@RequestBody @Valid MessagePayload<Sms> messagePayload) {
        LOGGER.info("REST: Sending mail notification synchronously for service {}", messagePayload.serviceID());
        messenger.send(messagePayload.toCommand(), Channel.SMS);
        return ResponseEntity.ok(MessageView.ok());
    }

}
