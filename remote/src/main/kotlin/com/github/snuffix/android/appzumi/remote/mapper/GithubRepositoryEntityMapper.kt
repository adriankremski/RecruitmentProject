package com.github.snuffix.android.appzumi.remote.mapper

import com.github.snuffix.android.appzumi.data.model.RepositoryEntity
import com.github.snuffix.android.appzumi.remote.model.GithubRepository
import java.util.*
import javax.inject.Inject

open class GithubRepositoryEntityMapper @Inject constructor(): EntityMapper<GithubRepository, RepositoryEntity> {

    override fun mapFromRemote(remoteModel: GithubRepository): RepositoryEntity = RepositoryEntity(
            id = UUID.randomUUID().toString(),
            repositoryName = remoteModel.name,
            userName = remoteModel.owner.login,
            avatarUrl = remoteModel.owner.avatar_url,
            description = remoteModel.description,
            source = "github"
    )
}