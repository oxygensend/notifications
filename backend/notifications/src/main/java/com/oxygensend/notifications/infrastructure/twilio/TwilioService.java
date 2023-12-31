package com.oxygensend.notifications.infrastructure.twilio;

import com.oxygensend.notifications.context.MessageService;
import com.oxygensend.notifications.domain.Channel;
import com.oxygensend.notifications.domain.Phone;
import com.oxygensend.notifications.domain.Sms;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class TwilioService implements MessageService<Phone, Sms> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioService.class);
    private final String fromPhoneNumber;

    public TwilioService(String fromPhoneNumber) {
        this.fromPhoneNumber = fromPhoneNumber;
    }

    @Override
    public void send(Sms message, Set<Phone> phoneNumbers) {
        try {
            for (Phone phoneNumber :
                    phoneNumbers
            ) {
                Message.creator(
                        new PhoneNumber(phoneNumber.fullNumber()),
                        new PhoneNumber(fromPhoneNumber),
                        message.content()
                ).create();
                LOGGER.info("SMS message sent successfully {} to {}", message, phoneNumber);
            }
        } catch (ApiException e) {
            LOGGER.info("Error sending SMS message: {} to {}, error message: {}", message, phoneNumbers, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Channel channel() {
        return Channel.SMS;
    }
}
