package com.job.test

import android.util.Log
import androidx.lifecycle.*
import com.job.test.data.db.entities.UsersEntity
import com.job.test.data.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val USER = "user"
const val PASSWORD = "password"
const val RETYPE_PASSWORD = "retype_password"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val userRepository : UserRepositoryImpl
) : ViewModel() {

    private val _loginState: MutableLiveData<LoginState> = MutableLiveData(LoginState.ShowLogin)
    val loginState : LiveData<LoginState>  = _loginState

    val userEdit = handle.getStateFlow(USER, InputWrapper())
    val passwordEdit = handle.getStateFlow(PASSWORD, InputWrapper())
    val retypePasswordEdit = handle.getStateFlow(RETYPE_PASSWORD, InputWrapper())
    private val _events = Channel<ScreenEvent>()

    private var focusedTextField = handle["focusedTextField"] ?: FocusedTextFieldKey.USER
        set(value) {
            field = value
            handle["focusedTextField"] = value
        }


    fun onNameEntered(input: String) {
        val errorId = InputValidator.getUserErrorOrNull(input)
        handle[USER] = userEdit.value.copy(value = input, errorId = errorId)
    }

    fun onNameImeActionClick() {
        _events.trySend(ScreenEvent.MoveFocus())
    }

    fun onTextFieldFocusChanged(key: FocusedTextFieldKey, isFocused: Boolean) {
        focusedTextField = if (isFocused) key else FocusedTextFieldKey.NONE
    }

    fun onPasswordEntered(input: String) {
        val errorId = InputValidator.getPasswordErrorOrNull(input)
        handle[PASSWORD] = passwordEdit.value.copy(value = input, errorId = errorId)
    }

    fun onRetypePasswordEntered(input: String) {
        val errorId = InputValidator.getPasswordErrorOrNull(input)
        handle[RETYPE_PASSWORD] = retypePasswordEdit.value.copy(value = input, errorId = errorId)
    }

    fun onUserEntered(input: String) {
        val errorId = InputValidator.getUserErrorOrNull(input)
        handle[USER] = userEdit.value.copy(value = input, errorId = errorId)
    }

    fun user(name : String) : LiveData<UsersEntity> {
        return userRepository.get(name)
    }

    fun checkLogin(user: String, password: String, retypePassword: String) {

        Log.d("checkLogin", "$user $password $retypePassword")

        viewModelScope.launch {
            if (retypePassword.isEmpty())
                userRepository.checkLogin(user,password).collect{
                    if (it == null)
                        _loginState.postValue(LoginState.UserPasswordNotFound(user))
                    else
                        successLogin(it,_loginState)
                }
            else if (password == retypePassword) {
                val usersEntity = UsersEntity(
                    id=null,
                    name = user,
                    password = password
                )
                viewModelScope.launch (Dispatchers.IO) {
                    userRepository.insert(usersEntity)
                    successLogin(usersEntity, _loginState)
                }
            }
            else {
                _loginState.postValue(LoginState.PasswordDoNotMatch)
            }

        }
    }

    private suspend fun successLogin(
        usersEntity: UsersEntity,
        _loginState: MutableLiveData<LoginState>
    ) {
        _loginState.postValue(LoginState.SuccessLogin(usersEntity))
    }

    fun clearRetypePassword() {
        _loginState.value = LoginState.UserPasswordNotFound(userEdit.value.value)
        handle[RETYPE_PASSWORD] = retypePasswordEdit.value.copy()
    }

}

sealed class LoginState {

    class SuccessLogin(val usersEntity: UsersEntity) : LoginState()
    class UserPasswordNotFound(user: String) : LoginState()
    object ShowLogin : LoginState()
    object PasswordDoNotMatch : LoginState()

}

