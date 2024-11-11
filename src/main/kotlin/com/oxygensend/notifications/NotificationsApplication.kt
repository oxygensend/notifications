package com.oxygensend.notifications

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class NotificationsApplication

fun main(args: Array<String>) {
    runApplication<NotificationsApplication>(*args)
}


