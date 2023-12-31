package com.oxygensend.notifications.infrastructure.twilio;

import com.oxygensend.notifications.context.MessageService;
import com.oxygensend.notifications.domain.Channel;
import com.oxygensend.notifications.domain.Sms;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwilioService implements MessageService<Sms> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioService.class);
    private final String fromPhoneNumber;

    public TwilioService(String fromPhoneNumber) {
        this.fromPhoneNumber = fromPhoneNumber;
    }

    @Override
    public void send(Sms message) {
        try {
            Message.creator(
                    new PhoneNumber(message.phoneNumber()),
                    new PhoneNumber(fromPhoneNumber),
                    message.body()
            ).create();
            LOGGER.info("SMS message sent successfully {}", message);
        } catch (ApiException e) {
            LOGGER.info("Error sending SMS message: {}, error message: {}", message, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Channel channel() {
        return Channel.SMS;
    }
}
