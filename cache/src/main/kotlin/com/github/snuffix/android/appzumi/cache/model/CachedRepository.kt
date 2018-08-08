package com.github.snuffix.android.appzumi.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "repositories")
data class CachedRepository(@PrimaryKey val repositoryId: String,
                            val repositoryName: String,
                            val userName: String,
                            val description: String?,
                            val avatarUrl: String?,
                            val source: String)

