package com.oxygensend.notifications.infrastructure.kafka

import com.oxygensend.commons_jdk.exception.ApiException
import com.oxygensend.notifications.context.config.NotificationProfile
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.errors.InvalidConfigurationException
import org.apache.kafka.common.security.auth.SecurityProtocol
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.CommonErrorHandler
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.serializer.DeserializationException
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.util.backoff.BackOff
import org.springframework.util.backoff.FixedBackOff
import java.util.*
import java.util.concurrent.ExecutionException
import javax.management.ServiceNotFoundException
import javax.naming.ServiceUnavailableException


@Profile(NotificationProfile.KAFKA)
@Configuration
@EnableConfigurationProperties(KafkaProperties::class)
class KafkaConsumerConfiguration internal constructor(private val kafkaProperties: KafkaProperties) {
    val logger: Logger = LoggerFactory.getLogger(KafkaConsumerConfiguration::class.java)

    init {
        verifyTopicsExistence()
    }


    @Bean
    fun notificationEventConsumerFactory(): ConsumerFactory<String, String> {
        return DefaultKafkaConsumerFactory(consumerProperties(), StringDeserializer(), errorHandlingDeserializer(String::class.java))
    }

    @Bean
    fun notificationEventListenerContainer(consumerFactory: ConsumerFactory<String, String>):
            ConcurrentKafkaListenerContainerFactory<String, String> {
        val containerFactory = ConcurrentKafkaListenerContainerFactory<String, String>();
        containerFactory.consumerFactory = consumerFactory
        containerFactory.setCommonErrorHandler(errorHandler())
        return containerFactory;
    }

    private fun consumerProperties(): MutableMap<String, Any?> {
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

    private fun secureConfigProperties(): MutableMap<String, Any?> {
        val secureProps: MutableMap<String, Any?> = HashMap()
        val securityProtocol = kafkaProperties.securityProtocol
        val kafkaSslSettings = kafkaProperties.ssl
        if (kafkaSslSettings.enabled) {
            return secureProps
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

    private fun verifyTopicsExistence() {
        val props = secureConfigProperties()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        val adminClient = AdminClient.create(props)
        adminClient.describeTopics(listOf(kafkaProperties.topic))
            .topicNameValues()
            .forEach { (key, value) ->
                try {
                    value.get()
                } catch (exception: Exception) {
                    when (exception) {
                        is InterruptedException, is ExecutionException -> {
                            logger.error("Error during topic description retrieval", exception)
                            throw InvalidConfigurationException("Failed to obtain topic description: $key")
                        }

                        else -> throw exception
                    }
                }


            }

    }

    private fun errorHandler(): CommonErrorHandler {
        val errorHandler = DefaultErrorHandler(this::consumerRecordRecover)
        errorHandler.addRetryableExceptions(ServiceNotFoundException::class.java, ApiException::class.java)
        errorHandler.setBackOffFunction { _, exception -> backOffConfig(exception) }
        return errorHandler
    }

    private fun backOffConfig(exception: java.lang.Exception): BackOff {
        val retry = kafkaProperties.retry
        return when (exception) {
            is ApiException -> FixedBackOff(retry.backOffPeriod, retry.maxRetries)
            is ServiceUnavailableException -> FixedBackOff(retry.backoffPeriodServiceUnavailable, FixedBackOff.UNLIMITED_ATTEMPTS);
            else -> FixedBackOff(0, 0)
        }
    }


    private fun consumerRecordRecover(consumerRecord: ConsumerRecord<*, *>, exception: Exception) {
        if (isDeserializationException(exception)) {
            val deserializationException: DeserializationException = exception as DeserializationException
            val brokenMessage = String(deserializationException.data)
            logger.info(
                "Skipping record with topic: {}, partition: {}, offset: {}, broken message: {}", consumerRecord.topic(),
                consumerRecord.partition(), consumerRecord.offset(), brokenMessage
            )
        } else {
            logger.info(
                "Consumer record exception - topic: {}, partition: {}, offset: {}, cause: {}", consumerRecord.topic(),
                consumerRecord.partition(), consumerRecord.offset(), exception.message
            )
        }
    }


    private fun isDeserializationException(exception: Exception): Boolean {
        return exception is DeserializationException
    }

    private fun <T> errorHandlingDeserializer(clazz: Class<T>): ErrorHandlingDeserializer<T> {
        return ErrorHandlingDeserializer(JsonDeserializer(clazz, false))
    }

}
