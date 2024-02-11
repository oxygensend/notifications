package com.oxygensend.notifications.domain

interface NotificationRepository {

    fun <S : Notification?> saveAll(entities: List<S>): MutableList<S>

}