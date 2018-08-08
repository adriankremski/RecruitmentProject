package com.github.snuffix.android.appzumi.cache.dao

import android.arch.persistence.room.*
import com.github.snuffix.android.appzumi.cache.model.CachedRepository

@Dao
abstract class CachedDao {
    @Query("SELECT * FROM repositories")
    abstract fun getRepositories(): List<CachedRepository>

    @Query("SELECT * FROM repositories WHERE repositoryId = :repositoryId")
    abstract fun getRepository(repositoryId: String): CachedRepository

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepository(repository: CachedRepository)

    @Query("DELETE FROM repositories")
    abstract fun deleteRepositories()
}
