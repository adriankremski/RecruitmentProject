package com.github.snuffix.android.appzumi.cache.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.github.snuffix.android.appzumi.cache.dao.CachedDao
import com.github.snuffix.android.appzumi.cache.model.CachedRepository
import javax.inject.Inject

@Database(entities = [(CachedRepository::class)], version = 1)
abstract class RepositoryDatabase @Inject constructor() : RoomDatabase() {
    abstract fun repositoriesDao(): CachedDao
}