package com.github.snuffix.android.appzumi.cache.mapper

import com.github.snuffix.android.appzumi.cache.model.CachedRepository
import com.github.snuffix.android.appzumi.data.model.RepositoryEntity
import javax.inject.Inject

open class CachedRepositoryMapper @Inject constructor() : Mapper<CachedRepository, RepositoryEntity> {
    override fun mapFromCached(cachedRepository: CachedRepository): RepositoryEntity {
        return RepositoryEntity(
                id = cachedRepository.repositoryId,
                repositoryName = cachedRepository.repositoryName,
                userName = cachedRepository.userName,
                description = cachedRepository.description,
                source = cachedRepository.source,
                avatarUrl = cachedRepository.avatarUrl)
    }

    override fun mapToCached(repositoryEntity: RepositoryEntity): CachedRepository = CachedRepository(
            repositoryId = repositoryEntity.id,
            repositoryName = repositoryEntity.repositoryName,
            userName = repositoryEntity.userName,
            description = repositoryEntity.description,
            source = repositoryEntity.source,
            avatarUrl = repositoryEntity.avatarUrl)
}
