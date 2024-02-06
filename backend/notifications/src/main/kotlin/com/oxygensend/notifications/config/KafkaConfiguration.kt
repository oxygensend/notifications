package com.oxygensend.notifications.config

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.security.auth.SecurityProtocol
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@EnableConfigurationProperties(KafkaProperties::class)
class KafkaConfiguration(private val kafkaProperties: KafkaProperties) {
    private fun consumerProperties(): Map<String, Any?> {
        val configProps: MutableMap<String, Any?> = HashMap()
        configProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        configProps[ConsumerConfig.GROUP_ID_CONFIG] = kafkaProperties.applicationId + UUID.randomUUID()
        configProps[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false
        configProps[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = kafkaProperties.autoOffsetReset
        configProps[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] = kafkaProperties.maxPollRecords
        configProps[ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG] = kafkaProperties.maxPollInterval
        configProps[ConsumerConfig.RETRY_BACKOFF_MS_CONFIG] = kafkaProperties.retryBackoffInMs
        configProps[ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG] = kafkaProperties.requestTimeoutMs
        configProps[ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG] = kafkaProperties.connectionsMaxIdleMs
        configProps.putAll(secureConfigProperties())
        return configProps
    }

    private fun secureConfigProperties(): Map<String, Any?> {
        val secureProps: MutableMap<String, Any?> = HashMap()
        val securityProtocol = kafkaProperties.securityProtocol
        val kafkaSslSettings = kafkaProperties.ssl
        if (kafkaSslSettings.enabled) {
            return secureProps;
        }

        secureProps[CommonClientConfigs.SECURITY_PROTOCOL_CONFIG] = securityProtocol!!.name
        if (securityProtocol == SecurityProtocol.SSL || securityProtocol == SecurityProtocol.SASL_SSL) {
            secureProps[SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG] = kafkaSslSettings.trustStore
            secureProps[SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG] = kafkaSslSettings.trustStorePassword
            secureProps[SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG] = kafkaSslSettings.trustStoreType
            secureProps[SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG] = kafkaSslSettings.keyStore
            secureProps[SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG] = kafkaSslSettings.keyStorePassword
            secureProps[SslConfigs.SSL_KEYSTORE_TYPE_CONFIG] = kafkaSslSettings.keyStoreType
            secureProps[SslConfigs.SSL_KEY_PASSWORD_CONFIG] = kafkaSslSettings.keyPassword
        }
        if (securityProtocol == SecurityProtocol.SASL_SSL) {
            secureProps[SaslConfigs.SASL_JAAS_CONFIG] = kafkaProperties.saslJaasConfig
            secureProps[SaslConfigs.SASL_MECHANISM] = kafkaProperties.saslMechanism
        }
        return secureProps
    }

    private fun verifyTopicsExistence() {}

    @Bean
    fun applicationScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)


}
