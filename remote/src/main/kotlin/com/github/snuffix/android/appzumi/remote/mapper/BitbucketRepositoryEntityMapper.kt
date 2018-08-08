package com.github.snuffix.android.appzumi.remote.mapper

import com.github.snuffix.android.appzumi.data.model.RepositoryEntity
import com.github.snuffix.android.appzumi.remote.model.BitbucketRepository
import java.util.*
import javax.inject.Inject

open class BitbucketRepositoryEntityMapper @Inject constructor() : EntityMapper<BitbucketRepository, RepositoryEntity> {

    override fun mapFromRemote(remoteModel: BitbucketRepository): RepositoryEntity = RepositoryEntity(
            id = UUID.randomUUID().toString(),
            repositoryName = remoteModel.name,
            userName = remoteModel.owner.username,
            avatarUrl = remoteModel.owner.links?.get("avatar")?.href ?: null,
            description = remoteModel.description,
            source = "bitbucket"
    )
}