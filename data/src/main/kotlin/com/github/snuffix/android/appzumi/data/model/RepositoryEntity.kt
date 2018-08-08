package com.github.snuffix.android.appzumi.data.model

data class RepositoryEntity(
        val id: String,
        val repositoryName: String,
        val userName: String,
        val description: String?,
        val avatarUrl: String?,
        val source: String)
