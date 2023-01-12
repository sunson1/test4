package com.job.test

import android.util.Log
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.FlowPreview

@Composable
fun WebViewScreen(navController: NavHostController) {


   val mainViewModel : MainViewModel = oneInstanceViewModel()

    AndroidView(
        factory = {
            WebView(it).apply {
                if (mainViewModel.usersEntity.value == null)
                    loadUrl("https://google.com.ua")
                else
                    loadUrl("https://google.com.ua?id=" + mainViewModel.usersEntity.value?.id)
            }
        }
    )
}

@Preview
@Composable
fun PreviewWebViewScreen() {
    val navController = rememberNavController()
    WebViewScreen(navController)
}
