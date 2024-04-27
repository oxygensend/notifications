package com.oxygensend.notifications.domain.service

import com.oxygensend.notifications.domain.Channel
import java.time.LocalDateTime

interface MessageService<R, C> {
    fun send(message: C, recipients: Set<R>)
    fun channel(): Channel?
    fun save(message: C, recipients: Set<R>, serviceID: String, requestId: String?, createdAt: LocalDateTime?): Int
}
