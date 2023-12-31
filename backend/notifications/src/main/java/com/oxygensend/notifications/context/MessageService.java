package com.oxygensend.notifications.context;

import com.oxygensend.notifications.domain.Channel;

import java.util.Set;

public interface MessageService<R, C> {
    void send(C message, Set<R> recipients);

    Channel channel();
}
