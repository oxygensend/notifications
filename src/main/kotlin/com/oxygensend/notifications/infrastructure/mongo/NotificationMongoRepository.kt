package com.oxygensend.notifications.infrastructure.mongo

import com.oxygensend.notifications.domain.history.Notification
import com.oxygensend.notifications.domain.history.NotificationRepository
import com.oxygensend.notifications.domain.history.part.FindNotificationsQuery
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class NotificationMongoRepository(
    private val importedNotificationMongoRepository: ImportedNotificationMongoRepository,
    private val mongoTemplate: MongoTemplate,
    private val adapter: NotificationMongoAdapter
) : NotificationRepository {
    override fun saveAll(entities: List<Notification>): List<Notification> {
        return importedNotificationMongoRepository.saveAll(entities.map { adapter.toDataSource(it) })
            .map { adapter.toDomain(it) }
    }

    override fun save(entity: Notification): Notification {
        return adapter.toDomain(importedNotificationMongoRepository.save(adapter.toDataSource(entity)));
    }

    override fun findAll(query: FindNotificationsQuery): Page<Notification> {
        val mongoQuery = Query()
            .with(query.pageable)
            .apply {
                query.search?.let {
                    addCriteria(
                        Criteria.where(NotificationMongo::content.name).regex(query.search, "i")
                            .orOperator(
                                Criteria.where(NotificationMongo::title.name).regex(query.search, "i")
                            )
                    )
                }
                query.recipient?.let {
                    addCriteria(
                        Criteria.where(NotificationMongo::recipient.name).`is`(query.recipient)
                    )
                }
                query.recipientId?.let {
                    addCriteria(
                        Criteria.where(NotificationMongo::recipientId.name).`is`(query.recipientId)
                    )
                }
                query.channel?.let { addCriteria(Criteria.where(NotificationMongo::channel.name).`is`(query.channel)) }
                query.serviceId?.let {
                    addCriteria(
                        Criteria.where(NotificationMongo::serviceId.name).`is`(query.serviceId)
                    )
                }
                query.requestId?.let {
                    addCriteria(
                        Criteria.where(NotificationMongo::requestId.name).`is`(query.requestId)
                    )
                }
                addCriteria(Criteria.where(NotificationMongo::deleted.name).`is`(false))
            }
            .with(query.sort)

        mongoTemplate.find(mongoQuery, NotificationMongo::class.java).map { adapter.toDomain(it) }
            .let {
                return PageableExecutionUtils.getPage(it, query.pageable) {
                    mongoTemplate.count(Query.of(mongoQuery).limit(-1).skip(-1), NotificationMongo::class.java)
                }
            }

    }

    override fun findById(id: UUID): Optional<Notification> {
        return importedNotificationMongoRepository.findById(id).map { adapter.toDomain(it) }
    }

}