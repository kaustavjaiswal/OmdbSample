package com.kaustavjaiswal.omdbsampleapp.dataSource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kaustavjaiswal.omdbsampleapp.dataSource.local.db.ShowDatabase
import com.kaustavjaiswal.omdbsampleapp.dataSource.remote.api.OmdbService
import com.kaustavjaiswal.omdbsampleapp.model.ShowData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class that works with [OmdbService] and [ShowDatabase] as a data sources.
 */
@Singleton
class SearchRepository @Inject constructor(
    private val service: OmdbService,
    private val showDatabase: ShowDatabase
) {
    /**
     * Search shows whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the data source.
     */
    fun getSearchResultStream(query: String): Flow<PagingData<ShowData>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { showDatabase.showDataDao().showDataByName(dbQuery) }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 10,
                enablePlaceholders = false
            ),
            remoteMediator = OmdbRemoteMediator(
                query,
                service,
                showDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}
