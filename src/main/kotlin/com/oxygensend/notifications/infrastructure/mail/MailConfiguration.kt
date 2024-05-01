package com.oxygensend.notifications.infrastructure.mail

import com.oxygensend.notifications.application.NotificationIdGenerator
import com.oxygensend.notifications.application.config.NotificationProfile
import com.oxygensend.notifications.domain.channel.Notifier
import com.oxygensend.notifications.domain.channel.mail.Mail
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Profile(NotificationProfile.MAIL)
@Configuration
@EnableConfigurationProperties(MailProperties::class)
class MailConfiguration {
    @Bean
    internal fun mailService(mailSender: JavaMailSender, mailProperties: MailProperties, notificationIdGenerator: NotificationIdGenerator): Notifier<Mail> {
        return MailService(mailSender, mailProperties.emailFrom, notificationIdGenerator)
    }

    @Bean
    internal fun mailSender(mailProperties: MailProperties): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = mailProperties.host
        mailSender.port = mailProperties.port
        mailSender.username = mailProperties.username
        mailSender.password = mailProperties.password
        mailSender.protocol = mailProperties.protocol
        val props = mailSender.javaMailProperties
        props["mail.smtp.auth"] = mailProperties.auth
        props["mail.smtp.starttls.enable"] = mailProperties.starttlsEnable
        props["mail.debug"] = mailProperties.debug
        if (mailProperties.testConnection) {
            mailSender.testConnection()
        }
        return mailSender
    }

}
