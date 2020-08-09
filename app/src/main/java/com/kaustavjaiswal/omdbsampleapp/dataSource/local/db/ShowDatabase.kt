package com.kaustavjaiswal.omdbsampleapp.dataSource.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kaustavjaiswal.omdbsampleapp.model.ShowData

@Database(
    entities = [ShowData::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class ShowDatabase : RoomDatabase() {

    abstract fun showDataDao(): ShowDataDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: ShowDatabase? = null

        fun getInstance(context: Context): ShowDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ShowDatabase::class.java, "showData.db"
            ).build()
    }
}