package com.job.test

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ShowLoginScreen() {

    val userFocusRequester = remember { FocusRequester() }
    val viewModel: LoginAutoViewModel = hiltViewModel()
    val user by viewModel.userEdit.collectAsStateWithLifecycle()
    val password by viewModel.passwordEdit.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val content = @Composable {
        Column() {

            RequireTextField(
                modifier = Modifier
                    .focusRequester(userFocusRequester)
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.USER,
                            isFocused = focusState.isFocused
                        )
                    }
                ,
                labelResId = R.string.user,
                keyboardOptions = remember {
                    KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                },
                inputWrapper = user,
                onValueChange = viewModel::onNameEntered,
                onImeKeyAction = viewModel::onNameImeActionClick
            )
            RequireTextField(
                modifier = Modifier
                    .focusRequester(userFocusRequester)
                    .onFocusChanged { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.PASSWORD,
                            isFocused = focusState.isFocused
                        )
                    }
                ,
                inputWrapper = password,
                onValueChange = viewModel::onPasswordEntered,
                onImeKeyAction = viewModel::onNameImeActionClick,
                labelResId = R.string.password

            )
        }
    }

    AlertDialog(
        onDismissRequest = {},
        confirmButton = { Button(onClick = {
            TODO()
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

