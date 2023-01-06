package com.job.test

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoginWindow() {
    val user = remember {mutableStateOf("") }
    val password = remember {mutableStateOf("") }

    val content = @Composable {
        Column() {
            TextField(value = user.value, onValueChange = {user.value = it} )
            TextField(value = password.value, onValueChange = {password.value = it} )
        }
    }

    AlertDialog(
        onDismissRequest = {},
        confirmButton = { Button(onClick = { TODO() }) {} },
        modifier = Modifier,
        dismissButton = { Button(onClick = { TODO() }) {
            Text("Cancel")
        } },
        title = { Text(text = "Login") },
        text =  content,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = contentColorFor(backgroundColor),
        properties = DialogProperties()    )

}