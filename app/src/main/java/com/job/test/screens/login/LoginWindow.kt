package com.job.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.job.test.screens.login.LoginState
import com.job.test.screens.login.LoginViewModel

@Composable
fun LoginWindow() {

    val loginViewModel: LoginViewModel = oneInstanceViewModel()
    val mainViewModel: MainViewModel = oneInstanceViewModel()
    val state by loginViewModel.loginState.observeAsState()

    when (state) {
        LoginState.ShowLogin -> ShowLoginScreen()
        is LoginState.UserPasswordNotFound -> ShowLoginScreen()
        is LoginState.SuccessLogin -> {
            mainViewModel.setUser((state as LoginState.SuccessLogin).usersEntity)
            ShowMainScreen()
        }
        LoginState.PasswordDoNotMatch -> loginViewModel.clearRetypePassword()
        null -> TODO()
    }
}

