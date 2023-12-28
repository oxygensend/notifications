package com.oxygensend.notifications.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Validated
@ConfigurationProperties(prefix = "kafka")
public record KafkaProperties(
        @NotEmpty String applicationId,
        @NotEmpty String bootstrapServers,
        @Positive int retryBackoffInMs,
        @Positive int requestTimeoutMs,
        @Positive int maxPollRecords,
        @Positive int maxPollInterval,
        @Positive int pollTimeoutMs,
        @Positive int connectionsMaxIdleMs,
        @Positive int consumerNumber,
        @NotEmpty String autoOffsetReset,
        @NotEmpty Map<KafkaTopic, String> topics,
        KafkaSsl ssl,
        SecurityProtocol securityProtocol,
        String saslJaasConfig,
        String saslMechanism

) {

    public static record KafkaSsl(
            boolean enabled,
            String keyStore,
            String keyStorePassword,
            String keyPassword,
            String keyStoreType,
            String trustStore,
            String trustStorePassword,
            String trustStoreType) {
    }


}
