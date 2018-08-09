package com.github.snuffix.android.appzumi.cache

import com.github.snuffix.android.appzumi.cache.database.RepositoryDatabase
import com.github.snuffix.android.appzumi.cache.mapper.CachedRepositoryMapper
import com.github.snuffix.android.appzumi.data.model.RepositoryEntity
import com.github.snuffix.android.appzumi.data.repository.RepositoryCacheSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepositoryCacheSourceImpl @Inject constructor(private val repositoryDatabase: RepositoryDatabase,
                                                    private val mapper: CachedRepositoryMapper,
                                                    private val cachePreferences: CachePreferences) :
        RepositoryCacheSource {

    private val EXPIRATION_TIME_IN_MILLIS = TimeUnit.DAYS.toMillis(1)

    override fun saveRepositories(repositories: List<RepositoryEntity>): Completable {
        return Completable.defer {
            repositoryDatabase.repositoriesDao().deleteRepositories()

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

    override fun getRepository(repositoryId: String): Single<RepositoryEntity> {
        return Single.defer {
            Single.just(repositoryDatabase.repositoriesDao().getRepository(repositoryId))
        }.map {
            mapper.mapFromCached(it)
        }
    }

    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(repositoryDatabase.repositoriesDao().getRepositories().isNotEmpty())
        }
    }

    override fun setLastCacheTime(lastCache: Long) {
        cachePreferences.lastCacheTime = lastCache
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME_IN_MILLIS
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return cachePreferences.lastCacheTime
    }
}