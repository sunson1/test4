package com.job.test.domain.repository

import androidx.lifecycle.LiveData
import com.job.test.data.db.entities.UsersEntity

interface UserRepository {
    fun insert(usersEntity: UsersEntity)
    fun update(usersEntity: UsersEntity)
    fun get(name: String) : LiveData<UsersEntity>
}