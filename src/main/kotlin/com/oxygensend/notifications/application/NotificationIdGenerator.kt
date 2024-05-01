package com.oxygensend.notifications.application

import org.springframework.stereotype.Component
import java.util.*


@Component
class NotificationIdGenerator {
    fun get(): UUID = UUID.randomUUID()
}