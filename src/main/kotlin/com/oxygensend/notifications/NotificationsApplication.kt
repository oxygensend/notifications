package com.oxygensend.notifications

import com.oxygensend.commons_jdk.exception.ExceptionConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
@Import(ExceptionConfiguration::class)
class NotificationsApplication

fun main(args: Array<String>) {
    runApplication<NotificationsApplication>(*args)
}


