package com.oxygensend.notifications.domain.history

import com.oxygensend.notifications.domain.history.part.FindNotificationsQuery
import org.springframework.data.domain.Page
import java.util.*

interface NotificationRepository {

    fun saveAll(entities: List<Notification>): List<Notification>
    fun save(entity: Notification): Notification
    fun findAll(query: FindNotificationsQuery): Page<Notification>
    fun findById(id: UUID): Optional<Notification>
}