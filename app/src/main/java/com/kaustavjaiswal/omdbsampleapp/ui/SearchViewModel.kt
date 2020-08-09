package com.kaustavjaiswal.omdbsampleapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kaustavjaiswal.omdbsampleapp.dataSource.SearchRepository
import com.kaustavjaiswal.omdbsampleapp.model.ShowData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel for the [SearchShowsActivity] screen.
 * The ViewModel works with the [SearchRepository] to get the data.
 */
@ExperimentalCoroutinesApi
class SearchViewModel @ViewModelInject constructor(
    private val repository: SearchRepository
) : ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<ShowData>>? = null
    var forceRefresh = false

    fun searchShows(queryString: String): Flow<PagingData<ShowData>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null && forceRefresh.not()) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<ShowData>> = repository.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        forceRefresh = false
        return newResult
    }
}