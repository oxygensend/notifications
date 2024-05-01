package com.oxygensend.notifications.domain.history

import com.oxygensend.notifications.domain.history.part.FindNotificationsQuery
import org.springframework.data.domain.Page
import java.util.*

interface NotificationRepository {

    fun <S : Notification?> saveAll(entities: List<S>): MutableList<S>
    fun <S : Notification?> save(entity: S & Any): S & Any
    fun findAll(query: FindNotificationsQuery): Page<Notification>
    fun findById(id: UUID): Optional<Notification>
}