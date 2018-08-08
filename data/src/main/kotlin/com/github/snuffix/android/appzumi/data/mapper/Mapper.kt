package com.github.snuffix.android.appzumi.data.mapper

interface Mapper<EntityModel, DomainModel> {
    fun mapToEntity(domainModel: DomainModel): EntityModel
    fun mapFromEntity(entityModel: EntityModel): DomainModel
}