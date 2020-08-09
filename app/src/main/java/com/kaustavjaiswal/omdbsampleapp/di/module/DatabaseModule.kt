package com.kaustavjaiswal.omdbsampleapp.di.module

import android.app.Application
import com.kaustavjaiswal.omdbsampleapp.dataSource.local.db.ShowDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * By using Hilt, all we need, to have a fully functional DI app with Omdb sample running is a Network Module and a Database Module.
 * The module is installed in the [ApplicationComponent] to ensure it is scoped application wide.
 */
@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = ShowDatabase.getInstance(application)
}