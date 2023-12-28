package com.oxygensend.notifications.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "mail")
public record MailProperties(String host,
                             int port,
                             String username,
                             String password,
                             String protocol,
                             boolean auth,
                             boolean starttlsEnable,
                             boolean debug,
                             boolean testConnection,
                             String emailFrom
) {

}
