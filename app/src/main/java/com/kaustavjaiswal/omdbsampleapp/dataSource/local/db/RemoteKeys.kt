package com.kaustavjaiswal.omdbsampleapp.dataSource.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * When we get the last item loaded from the PagingState, there's no way to know the index of the page it belonged to.
 * To solve this problem, we can add another table that stores the next and previous page keys for each ShowData;
 * we can call it remoteKeys. While this can be done in the ShowData table, creating a new table
 * for the next and previous remote keys associated with a ShowData allows us to have a better separation of concerns.
 */
@Entity(tableName = "remoteKeys")
data class RemoteKeys(
    @PrimaryKey val showImdbId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
