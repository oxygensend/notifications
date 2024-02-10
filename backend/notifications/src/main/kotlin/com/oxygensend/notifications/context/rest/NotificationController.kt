package com.oxygensend.notifications.context.rest

import com.oxygensend.notifications.context.Messenger
import org.slf4j.Logger
import org.slf4j.LoggerFactory

internal abstract class NotificationController(protected val messenger: Messenger) {
    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)
}