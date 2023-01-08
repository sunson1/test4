package com.job.test.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.job.test.data.db.dao.UsersDao
import com.job.test.data.db.entities.UsersEntity
import com.job.test.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(private val usersDao: UsersDao) : UserRepository {

    override fun insert(usersEntity: UsersEntity)  {
        usersDao.insert(usersEntity)
    }

    override fun update(usersEntity: UsersEntity) {
        usersDao.update(usersEntity)
    }

    override fun get(name: String): LiveData<UsersEntity> {
        return usersDao.get(name)
    }

    @WorkerThread
    fun checkLogin(user: String, password: String) = flow<UsersEntity?> {
        val usersEntity = usersDao.get(user,password)
        emit(usersEntity)
    }.flowOn(Dispatchers.IO)

}