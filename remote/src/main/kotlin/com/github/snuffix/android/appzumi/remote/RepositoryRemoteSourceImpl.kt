package com.github.snuffix.android.appzumi.remote

import com.github.snuffix.android.appzumi.data.model.RepositoryEntity
import com.github.snuffix.android.appzumi.data.repository.RepositoryRemoteSource
import com.github.snuffix.android.appzumi.remote.mapper.BitbucketRepositoryEntityMapper
import io.reactivex.Flowable
import com.github.snuffix.android.appzumi.remote.mapper.GithubRepositoryEntityMapper
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class RepositoryRemoteSourceImpl @Inject constructor(private val githubApiService: GithubApiService,
                                                     private val githubRepositoryEntityMapper: GithubRepositoryEntityMapper,
                                                     private val bitbucketApiService: BitbucketApiService,
                                                     private val bitbucketRepositoryEntityMapper: BitbucketRepositoryEntityMapper) :
        RepositoryRemoteSource {

    override fun getRepositories(): Flowable<List<RepositoryEntity>> {
        var bitbucketFlowable = bitbucketApiService
                .getRepositories()
                .map {
                    val repositories = mutableListOf<RepositoryEntity>()
                    it.values.forEach { repositories.add(bitbucketRepositoryEntityMapper.mapFromRemote(it)) }
                    repositories
                }

        var githubFlowable = githubApiService
                .getRepositories()
                .map {
                    val repositories = mutableListOf<RepositoryEntity>()
                    it.forEach { repositories.add(githubRepositoryEntityMapper.mapFromRemote(it)) }
                    repositories
                }

        return Flowable.zip(bitbucketFlowable, githubFlowable, BiFunction { bitbucketRepoList: List<RepositoryEntity>, githubRepoList: List<RepositoryEntity> ->
            bitbucketRepoList + githubRepoList
        })
    }
}