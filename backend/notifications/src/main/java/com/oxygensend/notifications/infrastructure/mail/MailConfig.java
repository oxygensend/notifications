package com.oxygensend.notifications.infrastructure.mail;

import com.oxygensend.notifications.config.MailProperties;
import jakarta.mail.MessagingException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class MailConfig {

    @Bean
    public MailService mailService(JavaMailSender mailSender, MailProperties mailProperties) {
        return new MailService(mailSender, mailProperties.emailFrom());
    }

    @Bean
    public JavaMailSender mailSender(MailProperties mailProperties) throws MessagingException {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.host());
        mailSender.setPort(mailProperties.port());
        mailSender.setUsername(mailProperties.username());
        mailSender.setPassword(mailProperties.password());
        mailSender.setProtocol(mailProperties.protocol());

        var props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", mailProperties.auth());
        props.put("mail.smtp.starttls.enable", mailProperties.starttlsEnable());
        props.put("mail.debug", mailProperties.debug());

        if (mailProperties.testConnection()) {
            mailSender.testConnection();
        }

        return mailSender;
    }
}
