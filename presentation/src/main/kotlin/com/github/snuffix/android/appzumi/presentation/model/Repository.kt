package com.github.snuffix.android.appzumi.presentation.model

data class Repository(
        val repositoryName: String,
        val userName: String,
        val description: String?,
        val avatarUrl: String?,
        val source: String)
