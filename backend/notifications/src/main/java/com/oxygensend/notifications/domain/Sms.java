package com.oxygensend.notifications.domain;

import jakarta.validation.constraints.NotEmpty;

public record Sms(@NotEmpty
                  String body,
                  @NotEmpty
                  String phoneNumber) {
}
