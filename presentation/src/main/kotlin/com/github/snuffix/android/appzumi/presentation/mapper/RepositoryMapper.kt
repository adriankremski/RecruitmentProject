package com.github.snuffix.android.appzumi.presentation.mapper

import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import com.github.snuffix.android.appzumi.presentation.model.Repository
import javax.inject.Inject

open class RepositoryMapper @Inject constructor() : Mapper<Repository, RepositoryDomainModel> {
    override fun mapFromModel(model: Repository): RepositoryDomainModel = RepositoryDomainModel(
            repositoryName = model.repositoryName,
            userName = model.userName,
            description = model.description,
            source = model.source,
            avatarUrl = model.avatarUrl)

    override fun mapToModel(repository: RepositoryDomainModel): Repository = Repository(
            repositoryName = repository.repositoryName,
            userName = repository.userName,
            description = repository.description,
            source = repository.source,
            avatarUrl = repository.avatarUrl)
}
