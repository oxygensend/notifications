package com.oxygensend.notifications.domain

interface DataSourceObjectAdapter<D,T>{
    fun toDomain(dbObject: T): D
    fun toDataSource(domainObject: D): T
}