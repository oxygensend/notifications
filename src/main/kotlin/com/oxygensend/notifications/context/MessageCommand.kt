package com.oxygensend.notifications.context

import com.oxygensend.notifications.context.dto.MessageDto
import com.oxygensend.notifications.context.rest.MessagePayload
import com.oxygensend.notifications.domain.message.Message
import com.oxygensend.notifications.domain.recipient.Recipient
import java.time.LocalDateTime
import java.util.stream.Collectors

data class MessageCommand<R, C>(
    val content: C,
    val recipients: Set<R>,
    val login: String?,
    val serviceId: String,
    val requestId: String?,
    val createdAt: LocalDateTime?
) {
    companion object {
        fun <R, M, C : MessageDto> forPayload(
            payload: MessagePayload<C>,
        ): MessageCommand<Recipient, Message> {
            val recipients = payload.content.recipients.stream().map { DomainFactory.createRecipient(it) }.collect(Collectors.toSet())
            return MessageCommand(
                DomainFactory.createMessage(payload.content),
                recipients,
                payload.login,
                payload.serviceId!!,
                payload.requestId,
                payload.createdAt
            )
        }
    }
}
