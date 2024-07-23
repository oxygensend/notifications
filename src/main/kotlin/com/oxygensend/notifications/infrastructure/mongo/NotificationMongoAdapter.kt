package com.oxygensend.notifications.infrastructure.mongo

import com.oxygensend.notifications.domain.DataSourceObjectAdapter
import com.oxygensend.notifications.domain.history.Notification
import org.springframework.stereotype.Service

@Service
internal class NotificationMongoAdapter : DataSourceObjectAdapter<Notification, NotificationMongo> {
    override fun toDomain(dbObject: NotificationMongo): Notification = Notification(
        dbObject.id,
        dbObject.title,
        dbObject.content,
        dbObject.recipient,
        dbObject.recipientId,
        dbObject.channel,
        dbObject.status,
        dbObject.serviceId,
        dbObject.requestId,
        dbObject.deleted,
        dbObject.createdAt,
        dbObject.sentAt,
        dbObject.seenAt
    )

    override fun toDataSource(domainObject: Notification): NotificationMongo = NotificationMongo(
        domainObject.id,
        domainObject.title,
        domainObject.content,
        domainObject.recipient,
        domainObject.recipientId,
        domainObject.channel,
        domainObject.status,
        domainObject.serviceId,
        domainObject.requestId,
        domainObject.deleted,
        domainObject.createdAt,
        domainObject.sentAt,
        domainObject.seenAt
    )

}