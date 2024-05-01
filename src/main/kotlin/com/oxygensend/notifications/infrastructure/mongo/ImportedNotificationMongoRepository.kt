package com.oxygensend.notifications.infrastructure.mongo

import com.oxygensend.notifications.domain.history.Notification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ImportedNotificationMongoRepository : MongoRepository<Notification, UUID> {

    override fun findAll(pageable: Pageable): Page<Notification>
}