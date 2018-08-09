package com.github.snuffix.android.appzumi.domain.repository

import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import io.reactivex.Flowable
import io.reactivex.Single

interface ReposDataRepository {
    fun getRepositories(forceRefresh: Boolean): Flowable<List<RepositoryDomainModel>>
    fun getRepositoryById(repositoryId: String): Single<RepositoryDomainModel>
}
