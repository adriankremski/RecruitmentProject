package com.github.snuffix.android.appzumi.cache

import com.github.snuffix.android.appzumi.cache.database.RepositoryDatabase
import com.github.snuffix.android.appzumi.cache.mapper.CachedRepositoryMapper
import com.github.snuffix.android.appzumi.data.model.RepositoryEntity
import com.github.snuffix.android.appzumi.data.repository.RepositoryCacheSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class RepositoryCacheSourceImpl @Inject constructor(private val repositoryDatabase: RepositoryDatabase,
                                                    private val mapper: CachedRepositoryMapper) :
        RepositoryCacheSource {

    override fun clearRepositories(): Completable {
        return Completable.defer {
            repositoryDatabase.repositoriesDao().deleteRepositories()
            Completable.complete()
        }
    }

    override fun saveRepositories(repositories: List<RepositoryEntity>): Completable {
        return Completable.defer {
            repositories.forEach {
                repositoryDatabase.repositoriesDao().insertRepository(mapper.mapToCached(it))
            }
            Completable.complete()
        }
    }

    override fun getRepositories(): Flowable<List<RepositoryEntity>> {
        return Flowable.defer {
            Flowable.just(repositoryDatabase.repositoriesDao().getRepositories())
        }.map {
            it.map { mapper.mapFromCached(it) }
        }
    }

    override fun getRepository(repositoryId: String): Flowable<RepositoryEntity> {
        return Flowable.defer {
            Flowable.just(repositoryDatabase.repositoriesDao().getRepository(repositoryId))
        }.map {
            mapper.mapFromCached(it)
        }
    }

    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(repositoryDatabase.repositoriesDao().getRepositories().isNotEmpty())
        }
    }
}