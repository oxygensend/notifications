package com.oxygensend.notifications.domain.channel.mail

import com.oxygensend.notifications.domain.message.Progress

interface MailNotifier {

    fun notify(mail: Mail): Progress
}