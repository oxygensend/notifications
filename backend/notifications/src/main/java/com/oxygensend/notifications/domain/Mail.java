package com.oxygensend.notifications.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record Mail(@NotEmpty
                   String subject,
                   @NotEmpty
                   String body,
                   @Email
                   String email) {
}
