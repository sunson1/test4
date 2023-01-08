package com.job.test

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginWindow() {

    if (StoreOwner.viewModelStoreOwner == null)
        StoreOwner.viewModelStoreOwner = LocalViewModelStoreOwner.current

    val loginViewModel: LoginViewModel = hiltViewModel()
    val mainViewModel: MainViewModel = hiltViewModel(StoreOwner.viewModelStoreOwner!!)
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

