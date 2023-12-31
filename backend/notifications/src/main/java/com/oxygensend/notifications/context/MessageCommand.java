package com.oxygensend.notifications.context;


import com.oxygensend.notifications.context.dto.MailDto;
import com.oxygensend.notifications.context.dto.SmsDto;
import com.oxygensend.notifications.context.rest.MessagePayload;
import com.oxygensend.notifications.domain.*;

import java.util.Set;
import java.util.stream.Collectors;

public record MessageCommand<R, C>(C content,
                                   Set<R> recipients,
                                   String login,
                                   String serviceID,
                                   String createdAt) {

    public static MessageCommand<Email, Mail> forMail(MessagePayload<MailDto> payload) {
        var recipients = payload.content().emails().stream().map(DomainFactory::from).collect(Collectors.toSet());
        return new MessageCommand<>(DomainFactory.from(payload.content()),
                                    recipients,
                                    payload.login(),
                                    payload.serviceID(),
                                    payload.createdAt());
    }

    public static MessageCommand<Phone, Sms> forSms(MessagePayload<SmsDto> payload) {
        return new MessageCommand<>(DomainFactory.from(payload.content()),
                                    payload.content().phoneNumbers(),
                                    payload.login(),
                                    payload.serviceID(),
                                    payload.createdAt());
    }

}
