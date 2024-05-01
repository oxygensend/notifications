package com.oxygensend.notifications.infrastructure.mail

import com.oxygensend.notifications.application.NotificationIdGenerator
import com.oxygensend.notifications.domain.history.part.NotificationStatus
import com.oxygensend.notifications.domain.channel.Notifier
import com.oxygensend.notifications.domain.channel.mail.Email
import com.oxygensend.notifications.domain.channel.mail.Mail
import com.oxygensend.notifications.domain.message.NotificationProgress
import com.oxygensend.notifications.domain.message.Progress
import com.oxygensend.notifications.domain.message.Recipient
import org.slf4j.LoggerFactory
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender


class MailService(
    private val mailSender: JavaMailSender,
    private val fromEmail: String,
    private val notificationIdGenerator: NotificationIdGenerator
) : Notifier<Mail> {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(MailService::class.java)
    }

    override fun notify(message: Mail): Progress {
        val notificationsStatus = HashMap<Recipient, NotificationProgress>()

        message.recipients.forEach {
            val notificationId = notificationIdGenerator.get()
            try {
                mailSender.send(createMessage(message, it))
                notificationsStatus[it] = NotificationProgress(notificationId, NotificationStatus.SENT)
                LOGGER.info("MAIL message {} sent successfully to {}", notificationId, it)
            } catch (e: MailException) {
                notificationsStatus[it] = NotificationProgress(notificationId, NotificationStatus.SENT)
                LOGGER.info("Error sending MAIL message: {} , with message: {}", message, e.message)
            }
        }

        return Progress(notificationsStatus)
    }

    private fun createMessage(mail: Mail, email: Email): SimpleMailMessage {
        val simpleMailMessage = SimpleMailMessage()
        simpleMailMessage.from = fromEmail
        simpleMailMessage.subject = mail.subject
        simpleMailMessage.text = mail.body
        simpleMailMessage.setTo(email.email)
        return simpleMailMessage;
    }
}
