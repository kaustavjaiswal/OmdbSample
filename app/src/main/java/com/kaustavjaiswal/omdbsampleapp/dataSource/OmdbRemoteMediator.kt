package com.kaustavjaiswal.omdbsampleapp.dataSource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kaustavjaiswal.omdbsampleapp.dataSource.local.db.RemoteKeys
import com.kaustavjaiswal.omdbsampleapp.dataSource.local.db.ShowDatabase
import com.kaustavjaiswal.omdbsampleapp.dataSource.remote.api.OmdbService
import com.kaustavjaiswal.omdbsampleapp.model.ShowData
import java.io.InvalidObjectException

private const val STARTING_PAGE_INDEX = 1

/**
 * [OmdbRemoteMediator] acts as a central mediator source, this [RemoteMediator] is the latest Paging 3,
 * implementation, released in alpha along with the Android 11 Beta update
 *
 * Offers multiple advantages over the existing Paging 2, implementation:
 *
 * More on this here:
 * https://android-developers.googleblog.com/2020/07/getting-on-same-page-with-paging-3.html, article dated 21/7
 */
@OptIn(ExperimentalPagingApi::class)
open class OmdbRemoteMediator(
    private val query: String,
    private val service: OmdbService,
    private val showDatabase: ShowDatabase
) : RemoteMediator<Int, ShowData>() {

    private val map = HashMap<String, String>()

    init {
        // Since the demo is hardcoded to show search with constraints,
        // the map is pre populated with these values
        map["apikey"] = "165695f8"
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ShowData>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }

        }

        try {
            map["s"] = query
            val apiResponse = service.searchOmdb(page, map)

            val shows = apiResponse.showData
            val endOfPaginationReached = shows.isEmpty()
            showDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    showDatabase.remoteKeysDao().clearRemoteKeys(query)
                    showDatabase.showDataDao().clearShowsForShowName(query)
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = shows.map {
                    RemoteKeys(showImdbId = it.imdbID, prevKey = prevKey, nextKey = nextKey)
                }
                showDatabase.remoteKeysDao().insertAll(keys)
                showDatabase.showDataDao().insertAll(shows)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ShowData>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }
            ?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                showDatabase.remoteKeysDao().remoteKeysRepoId(repo.imdbID)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ShowData>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                showDatabase.remoteKeysDao().remoteKeysRepoId(repo.imdbID)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ShowData>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.imdbID?.let { repoId ->
                showDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }
}