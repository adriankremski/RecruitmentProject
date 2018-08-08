package com.github.snuffix.android.appzumi.data.mapper

import com.github.snuffix.android.appzumi.data.model.RepositoryEntity
import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import javax.inject.Inject

open class RepositoryMapper @Inject constructor() : Mapper<RepositoryEntity, RepositoryDomainModel> {
    override fun mapFromEntity(model: RepositoryEntity): RepositoryDomainModel {
        return RepositoryDomainModel(
                id = model.id,
                repositoryName = model.repositoryName,
                userName = model.userName,
                description = model.description,
                source = model.source,
                avatarUrl = model.avatarUrl)
    }

    override fun mapToEntity(repository: RepositoryDomainModel): RepositoryEntity = RepositoryEntity(
            id = repository.id,
            repositoryName = repository.repositoryName,
            userName = repository.userName,
            description = repository.description,
            source = repository.source,
            avatarUrl = repository.avatarUrl)
}
