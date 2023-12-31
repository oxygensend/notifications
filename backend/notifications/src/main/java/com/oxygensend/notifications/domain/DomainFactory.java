package com.oxygensend.notifications.domain;

import com.oxygensend.notifications.context.dto.MailDto;
import com.oxygensend.notifications.context.dto.SmsDto;

public class DomainFactory {
    private DomainFactory() {
    }

    public static Mail from(MailDto mailDto) {
        return new Mail(mailDto.subject(), mailDto.body());
    }

    public static Sms from(SmsDto smsDto) {
        return new Sms(smsDto.body());

    }

    public static Email from(String email) {
        return new Email(email);
    }

}
