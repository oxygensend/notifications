package com.oxygensend.notifications.infrastructure.mongo

import com.oxygensend.notifications.domain.FindNotificationsQuery
import com.oxygensend.notifications.domain.Notification
import com.oxygensend.notifications.domain.NotificationRepository
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificationMongoRepository(
    private val importedNotificationMongoRepository: ImportedNotificationMongoRepository,
    private val mongoTemplate: MongoTemplate
) : NotificationRepository {
    override fun <S : Notification?> saveAll(entities: List<S>): MutableList<S> {
        return importedNotificationMongoRepository.saveAll(entities)
    }

    override fun <S : Notification?> save(entity: S & Any): S & Any {
        return importedNotificationMongoRepository.save(entity)
    }

    override fun findAll(query: FindNotificationsQuery): Page<Notification> {
        val mongoQuery = Query()
            .with(query.pageable)
            .apply {
                query.search?.let {
                    addCriteria(
                        Criteria.where(Notification::content.name).regex(query.search, "i")
                            .orOperator(
                                Criteria.where(Notification::title.name).regex(query.search, "i")
                            )
                    )
                }
                query.recipient?.let { addCriteria(Criteria.where(Notification::recipient.name).`is`(query.recipient)) }
                query.recipientId?.let { addCriteria(Criteria.where(Notification::recipientId.name).`is`(query.recipientId)) }
                query.channel?.let { addCriteria(Criteria.where(Notification::channel.name).`is`(query.channel)) }
                query.serviceId?.let { addCriteria(Criteria.where(Notification::serviceId.name).`is`(query.serviceId)) }
                query.requestId?.let { addCriteria(Criteria.where(Notification::requestId.name).`is`(query.requestId)) }
            }
            .with(query.sort)

        mongoTemplate.find(mongoQuery, Notification::class.java).let {
            return PageableExecutionUtils.getPage(it, query.pageable) {
                mongoTemplate.count(Query.of(mongoQuery).limit(-1).skip(-1), Notification::class.java)
            }
        }

    }

    override fun findById(id: UUID): Optional<Notification> {
        return importedNotificationMongoRepository.findById(id)
    }

}