package com.kaustavjaiswal.omdbsampleapp.dataSource.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaustavjaiswal.omdbsampleapp.model.ShowData

@Dao
interface ShowDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<ShowData>)

    @Query("SELECT * FROM showData WHERE title LIKE :queryString")
    fun showDataByName(queryString: String): PagingSource<Int, ShowData>

    @Query("DELETE FROM showData WHERE title LIKE :showName")
    suspend fun clearShowsForShowName(showName: String)
}