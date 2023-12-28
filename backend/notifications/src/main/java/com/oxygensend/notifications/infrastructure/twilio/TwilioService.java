package com.oxygensend.notifications.infrastructure.twilio;

import com.oxygensend.notifications.context.MessageService;
import com.oxygensend.notifications.domain.Channel;
import com.oxygensend.notifications.domain.Sms;

public class TwilioService implements MessageService<Sms> {
    @Override
    public void send(Sms message) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Channel channel() {
        return Channel.SMS;
    }
}
