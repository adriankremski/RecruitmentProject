package com.github.snuffix.android.appzumi

import com.github.snuffix.android.appzumi.data.mapper.RepositoryMapper
import com.github.snuffix.android.appzumi.data.repository.RepositoryCacheSource
import com.github.snuffix.android.appzumi.data.repository.RepositoryRemoteSource
import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import com.github.snuffix.android.appzumi.domain.repository.ReposDataRepository
import io.reactivex.Flowable
import javax.inject.Inject

class ReposDataRepositoryImpl @Inject constructor(
        private val repositoryCacheSource: RepositoryCacheSource,
        private val repositoryRemoteSource: RepositoryRemoteSource,
        private val repositoryMapper: RepositoryMapper) : ReposDataRepository {

    override fun getRepositories(): Flowable<List<RepositoryDomainModel>> = repositoryCacheSource
            .isCached()
            .flatMapPublisher { isCached ->
                if (isCached) {
                    repositoryCacheSource
                            .getRepositories()
                            .map { it.map { repositoryMapper.mapFromEntity(it) } }
                } else {
                    repositoryRemoteSource.getRepositories()
                            .flatMap {
                                repositoryCacheSource.saveRepositories(it).toSingle { it }.toFlowable()
                            }
                            .flatMap {
                                Flowable.just(it.map { repositoryMapper.mapFromEntity(it) })
                            }
                }
            }
}
