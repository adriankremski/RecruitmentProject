package com.github.snuffix.android.appzumi

import com.github.snuffix.android.appzumi.data.repository.RepositoryRemote
import com.github.snuffix.android.appzumi.data.mapper.RepositoryMapper
import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import com.github.snuffix.android.appzumi.domain.repository.ReposDataRepository
import io.reactivex.Flowable
import javax.inject.Inject

class ReposDataRepositoryImpl @Inject constructor(private val repositoryRemote: RepositoryRemote,
                                                  private val repositoryMapper: RepositoryMapper) : ReposDataRepository {
    override fun getRepositories(): Flowable<List<RepositoryDomainModel>> = repositoryRemote.getRepositories()
            .flatMap {
                Flowable.just(it.map { repositoryMapper.mapFromEntity(it) })
            }
}
