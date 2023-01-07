package com.job.test.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(name = "name_unique", value = ["name"],unique=true ),
    ]
)
data class UsersEntity (
    @PrimaryKey
    val id : Int?,
    val name : String,
    val password : String
)