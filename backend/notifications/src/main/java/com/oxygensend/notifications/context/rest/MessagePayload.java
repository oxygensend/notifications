package com.oxygensend.notifications.context.rest;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record MessagePayload<C>(@Valid
                         C content,
                         String login,
                         @NotEmpty
                         String serviceID,
                         String createdAt) {
}
