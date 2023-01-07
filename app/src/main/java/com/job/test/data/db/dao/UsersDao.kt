package com.job.test.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.job.test.data.db.entities.UsersEntity

@Dao
interface UsersDao {

    @Query("select * from users where name=:name")
    fun get(name: String): LiveData<UsersEntity>

    @Insert
    fun insert(usersEntity: UsersEntity)

    @Update
    fun update(usersEntity: UsersEntity)

}
