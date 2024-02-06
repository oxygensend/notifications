package com.oxygensend.notifications.context

import com.oxygensend.notifications.config.NotificationProperties
import com.oxygensend.notifications.context.authentication.AuthException
import com.oxygensend.notifications.context.authentication.Authentication
import com.oxygensend.notifications.domain.Channel
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@EnableConfigurationProperties(NotificationProperties::class)
@Component
class Messenger internal constructor(
    private val notificationProperties: NotificationProperties,
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
        val messageService = getMessageService<Any, Any>(channel)
        messageService.send(message.content as Any, (message.recipients as Set<Any>))
    }

    suspend fun <R, C> sendAsync(message: MessageCommand<R, C>, channel: Channel) {
        CoroutineScope(Dispatchers.IO).launch {
            send(message, channel)
        }
    }

    private fun authorize(message: MessageCommand<*, *>) {
        if (!notificationProperties.authEnabled) {
            logger.info("Authentication disabled, skipping")
            return
        }
        if (!notificationProperties.services.contains(message.serviceID)) {
            logger.error("Authentication failed, unauthorized service: {}", message.serviceID)
            throw RuntimeException("Unauthorizated")
        }
        if (message.login == null) {
            logger.error("Authentication failed, login is required")
            throw RuntimeException("Login is required")
        }
        try {
            authentication.authenticate(getAuthParameters(message))
        } catch (e: AuthException) {
            throw RuntimeException(e.message)
        }
    }

    private fun getAuthParameters(message: MessageCommand<*, *>): Map<String, String?> {
        return mapOf("password" to message.login, "hashedPassword" to notificationProperties.secret)
    }

    private fun <R, C> getMessageService(channel: Channel): MessageService<R, C> {
        val messageService = strategies[channel] as MessageService<R, C>?
        if (messageService == null) {
            logger.error("No message service found for channel: {}", channel)
            throw UnsupportedOperationException()
        }
        return messageService
    }

}
