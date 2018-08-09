package com.github.snuffix.android.appzumi.data.repository

import com.github.snuffix.android.appzumi.data.model.RepositoryEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface RepositoryCacheSource {
    fun getRepositories(): Flowable<List<RepositoryEntity>>
    fun saveRepositories(repositories: List<RepositoryEntity>): Completable
    fun getRepository(repositoryId: String): Flowable<RepositoryEntity>
    fun isCached(): Single<Boolean>
    fun isExpired(): Boolean
    fun setLastCacheTime(lastCache: Long)
}
