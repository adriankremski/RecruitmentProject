package com.github.snuffix.android.appzumi.domain.model

data class RepositoryDomainModel(
        val id: String,
        val repositoryName: String,
        val userName: String,
        val description: String?,
        val avatarUrl: String?,
        val source: String)
