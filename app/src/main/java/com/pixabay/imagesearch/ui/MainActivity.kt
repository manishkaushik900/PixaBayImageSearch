package com.pixabay.imagesearch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.pixabay.imagesearch.utils.ImageDownloader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val downloader = ImageDownloader(this)
        downloader.downloadFile("https://pixabay.com/get/gd633530a25ff1a68c565f37e278c215b78650926392edf43841971bfc710c6dd449b49794fd4d786d7f7e926a6006695_1280.jpg")


        setContent {
            MaterialTheme {
                val navController = rememberNavController()
//                SearchScreen()
                NavigationBuilder(navController)
            }
        }
    }
}