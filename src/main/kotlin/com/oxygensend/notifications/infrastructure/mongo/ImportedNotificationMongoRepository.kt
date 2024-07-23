package com.oxygensend.notifications.infrastructure.mongo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

internal interface ImportedNotificationMongoRepository : MongoRepository<NotificationMongo, UUID> {

    override fun findAll(pageable: Pageable): Page<NotificationMongo>
}