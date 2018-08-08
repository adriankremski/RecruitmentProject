package com.github.snuffix.android.appzumi.remote.model


data class GithubRepository(val owner: GithubRepositoryOwner, val name: String, val description: String?)
data class GithubRepositoryOwner(val login: String, val avatar_url: String?)