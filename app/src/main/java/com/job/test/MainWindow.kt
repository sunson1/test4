package com.job.test

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainWindow() {

    val navController    : NavHostController = rememberNavController()
    val loginDestination : String = Destinations.LOGIN

    Scaffold { paddingValues: PaddingValues ->

        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = loginDestination

        ) {

            composable(route = loginDestination) {
                LoginWindow()
            }

        }

    }
}


object Destinations {
    const val LOGIN = "login"
}