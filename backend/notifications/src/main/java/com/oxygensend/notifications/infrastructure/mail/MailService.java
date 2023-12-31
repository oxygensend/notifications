package com.oxygensend.notifications.infrastructure.mail;

import com.oxygensend.notifications.context.MessageService;
import com.oxygensend.notifications.domain.Channel;
import com.oxygensend.notifications.domain.Email;
import com.oxygensend.notifications.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Set;

public class MailService implements MessageService<Email, Mail> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender mailSender;
    private final String fromEmail;

    public MailService(JavaMailSender mailSender, String fromEmail) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    public void send(Mail message, Set<Email> emails) {
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setTo(emails.stream().map(Email::address).toArray(String[]::new));
        simpleMailMessage.setSubject(message.subject());
        simpleMailMessage.setText(message.body());

        try {
            mailSender.send(simpleMailMessage);
            LOGGER.info("MAIL message sent successfully {} to {}", message, emails);
        } catch (MailException e) {
            LOGGER.info("Error sending MAIL message: {} to {}, with message: {}", message, emails, e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public Channel channel() {
        return Channel.EMAIL;
    }
}
