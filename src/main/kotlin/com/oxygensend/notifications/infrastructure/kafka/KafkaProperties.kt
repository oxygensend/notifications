package com.oxygensend.notifications.infrastructure.kafka

import com.oxygensend.notifications.application.config.NotificationProfile.Companion.KAFKA
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.apache.kafka.common.security.auth.SecurityProtocol
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile
import org.springframework.validation.annotation.Validated


@Validated
@ConfigurationProperties(prefix = "kafka")
@Profile(KAFKA)
internal data class KafkaProperties(
    @field:NotBlank val applicationId: String?,
    @field:NotBlank val bootstrapServers: String?,
    @field:Positive val retryBackoffMs: Int?,
    @field:Positive val requestTimeoutMs: Int?,
    @field:Positive val maxPollRecords: Int?,
    @field:Positive val maxPollIntervalMs: Int?,
    @field:Positive val pollTimeoutMs: Int?,
    @field:Positive val connectionsMaxIdleMs: Int?,
    @field:Positive val consumerNumber: Int?,
    @field:NotBlank val autoOffsetReset: String?,
    @field:NotBlank val topic: String?,
    val ssl: KafkaSsl,
    val securityProtocol: SecurityProtocol?,
    val saslJaasConfig: String?,
    val saslMechanism: String?,
    @field:Valid val retry: Retry
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
        @field:Positive val maxRetries: Long,
        @field:Min(100) val backOffPeriod: Long,
        @field:Min(100) val backoffPeriodServiceUnavailable: Long
    )
}
