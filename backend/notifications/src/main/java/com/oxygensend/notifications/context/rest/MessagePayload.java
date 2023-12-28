package com.oxygensend.notifications.context.rest;


import com.oxygensend.notifications.context.MessageCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

record MessagePayload<T>(@Valid
                         T content,
                         String login,
                         @NotEmpty
                         String serviceID,
                         String createdAt) {

    public MessageCommand<T> toCommand() {
        return new MessageCommand<>(content, login, serviceID, createdAt);
    }
}
