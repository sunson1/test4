package com.job.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.job.test.data.db.entities.UsersEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    init {
        Log.d("MainViewModel","init")
    }

    private val _usersEntity : MutableLiveData<UsersEntity> = MutableLiveData()
    val usersEntity : LiveData<UsersEntity> = _usersEntity

    fun setUser(usersEntity: UsersEntity) {
        _usersEntity.value = usersEntity
    }

}
