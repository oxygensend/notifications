package com.oxygensend.notifications.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "mail")
public record MailProperties(@NotEmpty
                             String host,
                             @NotNull
                             int port,
                             String username,
                             String password,
                             @NotEmpty
                             String protocol,
                             boolean auth,
                             boolean starttlsEnable,
                             boolean debug,
                             boolean testConnection,
                             @NotEmpty
                             String emailFrom
) {

}
