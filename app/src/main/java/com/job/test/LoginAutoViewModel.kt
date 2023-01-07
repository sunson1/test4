package com.job.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.job.test.data.db.entities.UsersEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

const val USER = "user"
const val PASSWORD = "password"

@HiltViewModel
class LoginAutoViewModel @Inject constructor(
    private val handle: SavedStateHandle
) : ViewModel() {

    val userEdit = handle.getStateFlow(USER, InputWrapper())
    val passwordEdit = handle.getStateFlow(PASSWORD, InputWrapper())
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

    fun onUserEntered(input: String) {
        val errorId = InputValidator.getUserErrorOrNull(input)
        handle[USER] = userEdit.value.copy(value = input, errorId = errorId)
    }

    fun user(name : String)

}

