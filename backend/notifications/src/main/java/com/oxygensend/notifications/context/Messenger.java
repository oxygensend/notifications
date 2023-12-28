package com.oxygensend.notifications.context;

import com.oxygensend.notifications.config.NotificationProperties;
import com.oxygensend.notifications.context.authentication.AuthException;
import com.oxygensend.notifications.context.authentication.Authentication;
import com.oxygensend.notifications.domain.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EnableConfigurationProperties(NotificationProperties.class)
@Component
public class Messenger {

    private static final Logger LOGGER = LoggerFactory.getLogger(Messenger.class);
    private final NotificationProperties notificationProperties;
    private final Map<Channel, MessageService<?>> strategies;
    private final Authentication authentication;

    Messenger(NotificationProperties notificationProperties, List<MessageService<?>> messageServices, Authentication authentication) {
        this.notificationProperties = notificationProperties;
        this.strategies = messageServices.stream()
                                         .collect(Collectors.toMap(MessageService::channel, messageService -> messageService));
        this.authentication = authentication;
    }

    public <T> void send(MessageCommand<T> message, Channel channel) {
        authorize(message);
        var messageService = getMessageService(channel);
        messageService.send(message.content());
    }

    @Async
    public <T> void sendAsync(MessageCommand<T> message, Channel channel) {
        send(message, channel);
    }

    private void authorize(MessageCommand<?> message) {
        if (!notificationProperties.authEnabled()) {
            LOGGER.info("Authentication disabled, skipping");
            return;
        }

        if (!notificationProperties.services().contains(message.serviceID())) {
            LOGGER.error("Authentication failed, unauthorized service: {}", message.serviceID());
            throw new RuntimeException("Unauthorizated");
        }

        if (message.login() == null) {
            LOGGER.error("Authentication failed, login is required");
            throw new RuntimeException("Login is required");
        }

        try {
            authentication.authenticate(getAuthParameters(message));
        } catch (AuthException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Map<String, String> getAuthParameters(MessageCommand<?> message) {
        return Map.of("password", message.login(), "hashedPassword", notificationProperties.secret());
    }

    @SuppressWarnings("unchecked")
    private <T> MessageService<T> getMessageService(Channel channel) {
        MessageService<T> messageService = (MessageService<T>) strategies.get(channel);

        if (messageService == null) {
            LOGGER.error("No message service found for channel: {}", channel);
            throw new UnsupportedOperationException();
        }

        return messageService;
    }

}
