package com.job.test.di

import android.content.Context
import androidx.room.Room
import com.job.test.data.db.AppDatabase
import com.job.test.data.db.dao.UsersDao
import com.job.test.data.repository.UserRepositoryImpl
import com.job.test.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)

class AppModule {

    @Provides
    fun provideUserDao(database: AppDatabase) : UsersDao {
        return database.UsersDao()
    }

    @Provides
    fun provideUserRepository(usersDao: UsersDao) : UserRepository {
        return UserRepositoryImpl(usersDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "data.db"
        ).fallbackToDestructiveMigration().build()
    }


}

