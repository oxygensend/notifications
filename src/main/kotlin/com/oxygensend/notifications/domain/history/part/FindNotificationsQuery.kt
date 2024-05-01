package com.oxygensend.notifications.domain.history.part

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class FindNotificationsQuery(
    val pageable: Pageable,
    val search: String?,
    val recipient: String?,
    val recipientId: String?,
    val channel: Channel?,
    val serviceId: String?,
    val requestId: String?,
    val sort: Sort
)