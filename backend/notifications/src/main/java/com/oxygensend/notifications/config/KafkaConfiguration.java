package com.oxygensend.notifications.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaConfiguration.class);
    private final KafkaProperties kafkaProperties;

    public KafkaConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }


    private Map<String, Object> consumerProperties() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrapServers());
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.applicationId() + UUID.randomUUID());
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.autoOffsetReset());
        configProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaProperties.maxPollRecords());
        configProps.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, kafkaProperties.maxPollInterval());
        configProps.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, kafkaProperties.retryBackoffInMs());
        configProps.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProperties.requestTimeoutMs());
        configProps.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, kafkaProperties.connectionsMaxIdleMs());
        configProps.putAll(secureConfigProperties());
        return configProps;
    }

    private Map<String, Object> secureConfigProperties() {
        Map<String, Object> secureProps = new HashMap<>();
        var securityProtocol = kafkaProperties.securityProtocol();
        var kafkaSslSettings = kafkaProperties.ssl();
        secureProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol.name);

        if (securityProtocol == SecurityProtocol.SSL || securityProtocol == SecurityProtocol.SASL_SSL) {
            secureProps.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, kafkaSslSettings.trustStore());
            secureProps.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, kafkaSslSettings.trustStorePassword());
            secureProps.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, kafkaSslSettings.trustStoreType());
            secureProps.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, kafkaSslSettings.keyStore());
            secureProps.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, kafkaSslSettings.keyStorePassword());
            secureProps.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, kafkaSslSettings.keyStoreType());
            secureProps.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, kafkaSslSettings.keyPassword());
        }

        if (securityProtocol == SecurityProtocol.SASL_SSL) {
            secureProps.put(SaslConfigs.SASL_JAAS_CONFIG, kafkaProperties.saslJaasConfig());
            secureProps.put(SaslConfigs.SASL_MECHANISM, kafkaProperties.saslMechanism());
        }
        return secureProps;
    }



    private void verifyTopicsExistence() {
    }
}
