package com.oxygensend.notifications.context.dto;

import com.oxygensend.notifications.domain.Mail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record MailDto(@NotEmpty
                      String subject,
                      @NotEmpty
                      String body,
                      Set<@Email String> emails) {

    public Mail toDomain() {
        return new Mail(subject, body);
    }
}
