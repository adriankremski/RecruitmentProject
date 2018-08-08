package com.github.snuffix.android.appzumi.ui.injection.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.github.snuffix.android.appzumi.ReposDataRepositoryImpl
import com.github.snuffix.android.appzumi.cache.CachePreferences
import com.github.snuffix.android.appzumi.cache.RepositoryCacheSourceImpl
import com.github.snuffix.android.appzumi.cache.database.RepositoryDatabase
import com.github.snuffix.android.appzumi.cache.mapper.CachedRepositoryMapper
import com.github.snuffix.android.appzumi.data.repository.RepositoryRemoteSource
import com.github.snuffix.android.appzumi.domain.executor.PostExecutionThread
import com.github.snuffix.android.appzumi.domain.repository.ReposDataRepository
import com.github.snuffix.android.appzumi.data.mapper.RepositoryMapper
import com.github.snuffix.android.appzumi.data.repository.RepositoryCacheSource
import com.github.snuffix.android.appzumi.remote.*
import com.github.snuffix.android.appzumi.remote.mapper.BitbucketRepositoryEntityMapper
import com.github.snuffix.android.appzumi.remote.mapper.GithubRepositoryEntityMapper
import com.github.snuffix.android.appzumi.ui.BuildConfig
import com.github.snuffix.android.appzumi.ui.UiThread
import com.github.snuffix.android.appzumi.ui.injection.scopes.PerApplication
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @PerApplication
    fun githubApiService(): GithubApiService {
        return GithubApiServiceFactory.makeService(BuildConfig.DEBUG)
    }

    @Provides
    @PerApplication
    fun bitbucketApiService(): BitbucketApiService {
        return BitbucketServiceFactory.makeService(BuildConfig.DEBUG)
    }

    @Provides
    @PerApplication
    fun repositoryRemote(githubApiService: GithubApiService,
                         githubRepositoryEntityMapper: GithubRepositoryEntityMapper,
                         bitbucketApiService: BitbucketApiService,
                         bitbucketRepositoryEntityMapper: BitbucketRepositoryEntityMapper): RepositoryRemoteSource {
        return RepositoryRemoteSourceImpl(
                githubApiService = githubApiService,
                githubRepositoryEntityMapper = githubRepositoryEntityMapper,
                bitbucketApiService = bitbucketApiService,
                bitbucketRepositoryEntityMapper = bitbucketRepositoryEntityMapper)
    }

    @Provides
    @PerApplication
    internal fun repositoryDatabase(application: Application): RepositoryDatabase {
        return Room.databaseBuilder(application.applicationContext,
                RepositoryDatabase::class.java, "repositories.db")
                .build()
    }

    @Provides
    @PerApplication
    internal fun provideCachePreferences(context: Context): CachePreferences {
        return CachePreferences(context)
    }

    @Provides
    @PerApplication
    internal fun repositoryCacheSource(database: RepositoryDatabase,
                                       mapper: CachedRepositoryMapper,
                                       cachePreferences: CachePreferences): RepositoryCacheSource {
        return RepositoryCacheSourceImpl(database, mapper, cachePreferences)
    }

    @Provides
    @PerApplication
    fun reposDataRepository(
            repositoryCacheSource: RepositoryCacheSource,
            repositoryRemoteSource: RepositoryRemoteSource,
            repositoryMapper: RepositoryMapper): ReposDataRepository {
        return ReposDataRepositoryImpl(repositoryCacheSource, repositoryRemoteSource, repositoryMapper)
    }

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }
}
