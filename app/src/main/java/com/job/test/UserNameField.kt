package com.job.test

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.job.test.screens.login.LoginViewModel

@Composable
fun UserNameField() {

    val userFocusRequester = remember { FocusRequester() }
    val loginViewModel: LoginViewModel = hiltViewModel()
    val user by loginViewModel.userEdit.collectAsState()

    RequireTextField(
        modifier = Modifier
            .focusRequester(userFocusRequester)
            .onFocusChanged { focusState ->
                loginViewModel.onTextFieldFocusChanged(
                    key = FocusedTextFieldKey.USER,
                    isFocused = focusState.isFocused
                )
            }
        ,
        inputWrapper = user,
        labelResId = R.string.user,
        keyboardOptions = remember {
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        },
        onValueChange = loginViewModel::onNameEntered,
        onImeKeyAction = loginViewModel::onNameImeActionClick,
    )
}