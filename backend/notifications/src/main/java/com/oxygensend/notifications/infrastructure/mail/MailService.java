package com.oxygensend.notifications.infrastructure.mail;

import com.oxygensend.notifications.context.MessageService;
import com.oxygensend.notifications.domain.Channel;
import com.oxygensend.notifications.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailService implements MessageService<Mail> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender mailSender;
    private final String fromEmail;

    public MailService(JavaMailSender mailSender, String fromEmail) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    public void send(Mail message) {
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setTo(message.email());
        simpleMailMessage.setSubject(message.subject());
        simpleMailMessage.setText(message.body());

        try {
            mailSender.send(simpleMailMessage);
        } catch (MailException e) {
            LOGGER.info("Error sending email: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public Channel channel() {
        return Channel.EMAIL;
    }
}
