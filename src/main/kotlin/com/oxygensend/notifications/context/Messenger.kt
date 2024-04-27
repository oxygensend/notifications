package com.oxygensend.notifications.context

import com.oxygensend.notifications.context.authentication.AuthException
import com.oxygensend.notifications.context.authentication.Authentication
import com.oxygensend.notifications.context.config.NotificationsProperties
import com.oxygensend.notifications.domain.Channel
import com.oxygensend.notifications.domain.service.MessageService
import com.oxygensend.notifications.domain.exception.ForbiddenException
import com.oxygensend.notifications.domain.exception.UnauthorizedException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
internal class Messenger(
    private val notificationsProperties: NotificationsProperties,
    messageServices: List<MessageService<*, *>>,
    private val authentication: Authentication
) {
    private val strategies: Map<Channel, MessageService<*, *>>
    private val logger = LoggerFactory.getLogger(Messenger::class.java)

    init {
        strategies = messageServices.stream()
            .collect(
                Collectors.toMap(
                    { obj: MessageService<*, *> -> obj.channel() },
                    { messageService: MessageService<*, *> -> messageService })
            )
    }

    fun <R, C> send(message: MessageCommand<R, C>, channel: Channel) {
        authorize(message)
        val messageService = getMessageService<R, C>(channel)
        messageService.send(message.content, message.recipients)
        saveNotifications(message, channel)
    }

    suspend fun <R, C> sendAsync(message: MessageCommand<R, C>, channel: Channel) {
        CoroutineScope(Dispatchers.IO).launch {
            send(message, channel)
        }
    }

    private fun authorize(message: MessageCommand<*, *>) {
        if (!notificationsProperties.authEnabled) {
            logger.info("Authentication disabled, skipping")
            return
        }
        if (!notificationsProperties.services.contains(message.serviceId)) {
            logger.error("Authorization failed, unauthorized service: {}", message.serviceId)
            throw ForbiddenException("Access denied for service:  $message.serviceId")
        }
        if (message.login == null) {
            logger.error("Access denied, login is required")
            throw ForbiddenException("Access denied, login is required")
        }
        try {
            authentication.authenticate(getAuthParameters(message))
        } catch (e: AuthException) {
            throw UnauthorizedException(e.message ?: "Unauthorized")
        }
    }

    private fun getAuthParameters(message: MessageCommand<*, *>): Map<String, String?> {
        return mapOf("password" to message.login, "hashedPassword" to notificationsProperties.secret)
    }

    private fun <R, C> getMessageService(channel: Channel): MessageService<R, C> {
        val messageService = strategies[channel] as MessageService<R, C>?
        if (messageService == null) {
            logger.error("No message service found for channel: {}", channel)
            throw UnsupportedOperationException()
        }
        return messageService
    }

    private fun <R, C> saveNotifications(message: MessageCommand<R, C>, channel: Channel) {
        if (!notificationsProperties.storeInDatabase) {
            return
        }

        val messageService = getMessageService<R, C>(channel)
        val numberOfSavedNotifications = messageService.save(message.content, message.recipients, message.serviceId, message.requestId, message.createdAt)
        logger.info("Notifications in number {} saved successfully for request id: {}", numberOfSavedNotifications, message.requestId)
    }

}
