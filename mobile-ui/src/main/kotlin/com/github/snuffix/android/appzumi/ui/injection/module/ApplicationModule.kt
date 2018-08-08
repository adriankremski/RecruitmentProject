package com.github.snuffix.android.appzumi.ui.injection.module

import android.app.Application
import android.content.Context
import com.github.snuffix.android.appzumi.ReposDataRepositoryImpl
import com.github.snuffix.android.appzumi.data.repository.RepositoryRemote
import com.github.snuffix.android.appzumi.domain.executor.PostExecutionThread
import com.github.snuffix.android.appzumi.domain.repository.ReposDataRepository
import com.github.snuffix.android.appzumi.data.mapper.RepositoryMapper
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
                         bitbucketRepositoryEntityMapper: BitbucketRepositoryEntityMapper): RepositoryRemote {
        return RepositoryRemoteImpl(
                githubApiService = githubApiService,
                githubRepositoryEntityMapper = githubRepositoryEntityMapper,
                bitbucketApiService = bitbucketApiService,
                bitbucketRepositoryEntityMapper = bitbucketRepositoryEntityMapper)
    }

    @Provides
    @PerApplication
    fun reposDataRepository(repositoryRemote: RepositoryRemote, repositoryMapper: RepositoryMapper): ReposDataRepository {
        return ReposDataRepositoryImpl(repositoryRemote, repositoryMapper)
    }

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }
}
