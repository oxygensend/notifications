package com.oxygensend.notifications.context

import com.oxygensend.notifications.domain.Channel

interface MessageService<R, C> {
    fun send(message: C, recipients: Set<R>)
    fun channel(): Channel?
}
