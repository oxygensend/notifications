package com.oxygensend.notifications.config

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive
import org.apache.kafka.common.security.auth.SecurityProtocol
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

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
    val topics: @NotEmpty Map<KafkaTopic, String>?,
    val ssl: KafkaSsl,
    val securityProtocol: SecurityProtocol?,
    val saslJaasConfig: String?,
    val saslMechanism: String?
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
}
