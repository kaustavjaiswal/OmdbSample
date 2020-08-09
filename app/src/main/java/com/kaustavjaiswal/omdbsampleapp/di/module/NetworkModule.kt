package com.kaustavjaiswal.omdbsampleapp.di.module

import com.kaustavjaiswal.omdbsampleapp.dataSource.remote.api.OmdbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * By using Hilt, all we need, to have a fully functional DI app with Omdb sample running is a Network Module and a Database Module.
 * The module is installed in the [ApplicationComponent] to ensure it is scoped application wide.
 */
@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder()
        return httpClient
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideOmdbService(okHttpClient: OkHttpClient): OmdbService {
        return Retrofit.Builder()
            .baseUrl(OmdbService.ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(OmdbService::class.java)
    }
}