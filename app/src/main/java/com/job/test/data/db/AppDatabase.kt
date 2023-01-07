package com.job.test.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.job.test.data.db.dao.UsersDao
import com.job.test.data.db.entities.UsersEntity

@Database(
    entities = [UsersEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun UsersDao(): UsersDao
/*

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: _buildDatabase(requireNotNull(context)).also {
                    INSTANCE = it
                }
            }

        fun _buildDatabase(context: Context): AppDatabase {
            val database = buildDatabase(context.applicationContext)
            return database
        }

        private fun buildDatabase(context: Context): AppDatabase {
            val applicationContext = context.applicationContext
            val result = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "test.db"
            ).build()
            return result
        }
    }

*/
}