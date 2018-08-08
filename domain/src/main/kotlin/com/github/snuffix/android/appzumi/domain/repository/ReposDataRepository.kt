package com.github.snuffix.android.appzumi.domain.repository

import com.github.snuffix.android.appzumi.domain.model.RepositoryDomainModel
import io.reactivex.Flowable

interface ReposDataRepository {
    fun getRepositories(): Flowable<List<RepositoryDomainModel>>
}
