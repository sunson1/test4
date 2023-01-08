package com.job.test

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ShowLoginScreen() {

    val userFocusRequester = remember { FocusRequester() }
    val loginViewModel: LoginViewModel = hiltViewModel()
    val user by loginViewModel.userEdit.collectAsState()
    val password by loginViewModel.passwordEdit.collectAsState()
    val retypePassword by loginViewModel.retypePasswordEdit.collectAsState()
    val context = LocalContext.current

    val content = @Composable {
        Column {

            UserNameField()
            PasswordField(password, R.string.password,loginViewModel::onPasswordEntered)
            if (loginViewModel.loginState.value is LoginState.UserPasswordNotFound)
                PasswordField(retypePassword,R.string.retype_password,loginViewModel::onRetypePasswordEntered)

        }
    }

    AlertDialog(
        onDismissRequest = {},
        confirmButton = { Button(onClick = {
            Log.d("checkLogin", "onClick " + user.value + " " + password.value + " " + retypePassword.value )
            loginViewModel.checkLogin(user.value,password.value,retypePassword.value)
        },
            enabled = InputValidator.getUserErrorOrNull(user.value) == null
                    && InputValidator.getPasswordErrorOrNull(password.value) == null
        ) { Text(text = "Login") } },
        modifier = Modifier,
        dismissButton = { Button(onClick = {
            Log.d("onDismissRequest",context.findActivity().toString() + " finish")
            context.findActivity()?.finish()
        }) {
            Text("Cancel")
        } },
        title = { Text(text = "Login") },
        text =  content,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = contentColorFor(SnackbarDefaults.backgroundColor),
        properties = DialogProperties() )
}

fun Context.findActivity(): ComponentActivity? {
    return when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}

