package com.github.snuffix.android.appzumi.presentation.mapper

import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import com.github.snuffix.android.appzumi.presentation.model.Repository
import javax.inject.Inject

open class RepositoryMapper @Inject constructor() : Mapper<Repository, RepositoryDomainModel> {
    override fun mapToModel(repository: RepositoryDomainModel): Repository = Repository(
            id = repository.id,
            repositoryName = repository.repositoryName,
            userName = repository.userName,
            description = repository.description,
            source = repository.source,
            avatarUrl = repository.avatarUrl)
}
