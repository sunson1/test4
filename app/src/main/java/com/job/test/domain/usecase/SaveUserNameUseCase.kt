package com.job.test.domain.usecase

import com.job.test.data.db.entities.UsersEntity
import com.job.test.domain.repository.UserRepository

class SaveUserNameUseCase (private val userRepository: UserRepository) {

    fun execute(param: UsersEntity)  {
        if (param.id == null)
            return userRepository.insert(param)
        else
            return userRepository.update(param)
    }

}