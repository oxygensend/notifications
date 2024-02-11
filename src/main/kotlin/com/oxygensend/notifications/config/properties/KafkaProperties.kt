package com.oxygensend.notifications.config.properties

import com.oxygensend.notifications.config.NotificationProfile
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.apache.kafka.common.security.auth.SecurityProtocol
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile
import org.springframework.validation.annotation.Validated


@Profile(NotificationProfile.KAFKA)
@Validated
@ConfigurationProperties(prefix = "kafka")
data class KafkaProperties(
    val applicationId: @NotEmpty String,
    val bootstrapServers: @NotEmpty String,
    val retryBackoffInMs: @Positive Int,
    val requestTimeoutMs: @Positive Int,
    val maxPollRecords: @Positive Int,
    val maxPollInterval: @Positive Int,
    val pollTimeoutMs: @Positive Int,
    val connectionsMaxIdleMs: @Positive Int,
    val consumerNumber: @Positive Int,
    val autoOffsetReset: @NotEmpty String,
    val topic: @NotEmpty String,
    val ssl: KafkaSsl,
    val securityProtocol: SecurityProtocol?,
    val saslJaasConfig: String?,
    val saslMechanism: String?,
    val retry: Retry
) {
    data class KafkaSsl(
        val enabled: Boolean,
        val keyStore: String?,
        val keyStorePassword: String?,
        val keyPassword: String?,
        val keyStoreType: String?,
        val trustStore: String?,
        val trustStorePassword: String?,
        val trustStoreType: String?
    ) {

    }

    data class Retry(
        val maxRetries: @Positive Long,
        val backOffPeriod: @Min(100) @NotNull Long,
        val backoffPeriodServiceUnavailable: @Min(100) @NotNull Long
    )
}
