package com.kaustavjaiswal.omdbsampleapp.dataSource.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remoteKeys WHERE showImdbId = :showId")
    suspend fun remoteKeysRepoId(showId: String): RemoteKeys?

    @Query("DELETE FROM remoteKeys WHERE showImdbId = :showId")
    suspend fun clearRemoteKeys(showId: String)
}

