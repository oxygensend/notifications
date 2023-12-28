package com.oxygensend.notifications.context;

import com.oxygensend.notifications.domain.Channel;

public interface MessageService<T> {
    void send(T message);

    Channel channel();
}
