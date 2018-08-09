package com.github.snuffix.android.appzumi

import com.github.snuffix.android.appzumi.data.mapper.RepositoryMapper
import com.github.snuffix.android.appzumi.data.repository.RepositoryCacheSource
import com.github.snuffix.android.appzumi.data.repository.RepositoryRemoteSource
import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import com.github.snuffix.android.appzumi.domain.repository.ReposDataRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class ReposDataRepositoryImpl @Inject constructor(
        private val repositoryCacheSource: RepositoryCacheSource,
        private val repositoryRemoteSource: RepositoryRemoteSource,
        private val repositoryMapper: RepositoryMapper) : ReposDataRepository {

    override fun getRepositoryById(repositoryId: String): Single<RepositoryDomainModel> {
        return repositoryCacheSource
                .getRepository(repositoryId)
                .flatMap { Single.just(repositoryMapper.mapFromEntity(it)) }
    }

    override fun getRepositories(forceRefresh: Boolean): Flowable<List<RepositoryDomainModel>> {
        return if (forceRefresh) {
            getRepositoriesFromRemote()
        } else {
            repositoryCacheSource
                    .isCached()
                    .flatMapPublisher { isCached ->
                        if (isCached && !repositoryCacheSource.isExpired()) {
                            repositoryCacheSource
                                    .getRepositories()
                                    .map { it.map { repositoryMapper.mapFromEntity(it) } }
                        } else {
                            getRepositoriesFromRemote()
                        }
                    }
        }
    }

    private fun getRepositoriesFromRemote(): Flowable<List<RepositoryDomainModel>> = repositoryRemoteSource.getRepositories()
            .flatMap {
                repositoryCacheSource.saveRepositories(it)
                        .toSingle { it }
                        .toFlowable()
                        .doOnComplete { repositoryCacheSource.setLastCacheTime(System.currentTimeMillis()) }
            }
            .flatMap {
                Flowable.just(it.map { repositoryMapper.mapFromEntity(it) })
            }
}
