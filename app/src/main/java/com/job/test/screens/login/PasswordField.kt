package com.job.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import com.job.test.screens.common.InputWrapper
import com.job.test.screens.login.LoginViewModel

@Composable
fun PasswordField(password: InputWrapper, labelResId: Int, onValueChange: OnValueChange ) {

    val userFocusRequester = remember { FocusRequester() }
    val loginViewModel: LoginViewModel = oneInstanceViewModel()

    RequireTextField(
        modifier = Modifier
            .focusRequester(userFocusRequester)
            .onFocusChanged { focusState ->
                loginViewModel.onTextFieldFocusChanged(
                    key = FocusedTextFieldKey.PASSWORD,
                    isFocused = focusState.isFocused
                )
            }
        ,
        inputWrapper = password,
        labelResId = labelResId,
        onValueChange = onValueChange,
        onImeKeyAction = loginViewModel::onNameImeActionClick,
        //visualTransformation = PasswordVisualTransformation()

        )
}