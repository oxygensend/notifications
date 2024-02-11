package com.oxygensend.notifications.domain

import org.springframework.data.domain.Page

interface NotificationRepository {

    fun <S : Notification?> saveAll(entities: List<S>): MutableList<S>

    fun findAll(query: FindNotificationsQuery): Page<Notification>

}