package com.oxygensend.notifications.context.queue;

import com.oxygensend.notifications.domain.Notification;

public interface NotificationListener {

    void onNotification(Notification notification);
}
