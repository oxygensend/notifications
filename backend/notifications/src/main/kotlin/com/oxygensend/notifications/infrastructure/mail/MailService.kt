package com.oxygensend.notifications.infrastructure.mail

import com.oxygensend.notifications.context.MessageService
import com.oxygensend.notifications.domain.Channel
import com.oxygensend.notifications.domain.Email
import com.oxygensend.notifications.domain.Mail
import org.slf4j.LoggerFactory
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender


class MailService(private val mailSender: JavaMailSender, private val fromEmail: String) :
    MessageService<Email, Mail> {
    override fun send(message: Mail, recipients: Set<Email>) {
        val simpleMailMessage = SimpleMailMessage()
        simpleMailMessage.from = fromEmail
        simpleMailMessage.subject = message.subject
        simpleMailMessage.text = message.body
        simpleMailMessage.setTo(*recipients.map { it.address }.toTypedArray());

        try {
            mailSender.send(simpleMailMessage)
            LOGGER.info("MAIL message sent successfully {} to {}", message, recipients)
        } catch (e: MailException) {
            LOGGER.info("Error sending MAIL message: {} to {}, with message: {}", message, recipients, e.message)
            throw RuntimeException()
        }
    }

    override fun channel(): Channel {
        return Channel.EMAIL
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MailService::class.java)
    }
}
