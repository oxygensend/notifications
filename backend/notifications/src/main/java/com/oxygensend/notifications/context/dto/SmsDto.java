package com.oxygensend.notifications.context.dto;

import com.oxygensend.notifications.domain.Phone;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;


public record SmsDto(@NotEmpty
                     String body,
                     Set<Phone> phoneNumbers) {


}
