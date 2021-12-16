package com.app.meister_search.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskArchive::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    abstract fun taskArchiveDao(): TaskArchiveDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {

            if (INSTANCE == null) synchronized(AppDatabase::class.java) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(
                        context, AppDatabase::class.java, "APP_DATABASE"
                    ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

                }

            }

            return INSTANCE

        }
    }
}