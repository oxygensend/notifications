package com.oxygensend.notifications.context.rest

import com.oxygensend.notifications.config.NotificationProfile.Companion.WHATSAPP_REST
import com.oxygensend.notifications.context.Messenger
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/notifications")
@Profile(WHATSAPP_REST)
internal class WhatsappController(messenger: Messenger): NotificationController(messenger)
