package com.oxygensend.notifications.application.rest

data class PagedListView<T>(
    val data: List<T>,
    val numberOfElements: Long,
    val currentPage: Int,
    val totalPages: Int
)

