package com.job.test.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.job.test.data.db.entities.UsersEntity

@Dao
interface UsersDao {

    @Query("select * from users where name=:name")
    fun find(name: String): UsersEntity

}
