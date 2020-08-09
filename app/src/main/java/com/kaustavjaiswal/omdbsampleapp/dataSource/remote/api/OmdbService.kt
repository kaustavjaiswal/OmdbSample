package com.kaustavjaiswal.omdbsampleapp.dataSource.remote.api

import com.kaustavjaiswal.omdbsampleapp.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Interface to the OmdbApi
 */
interface OmdbService {

    @GET("/")
    suspend fun searchOmdb(
        @Query("page") page: Int,
        @QueryMap options: Map<String, String>
    ): SearchResponse

    companion object {
        const val ENDPOINT = "https://www.omdbapi.com"
    }
}
