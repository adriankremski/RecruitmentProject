package com.github.snuffix.android.appzumi.presentation.model

data class Repository(
        val id: String,
        val repositoryName: String,
        val userName: String,
        val description: String?,
        val avatarUrl: String?,
        val source: String)
