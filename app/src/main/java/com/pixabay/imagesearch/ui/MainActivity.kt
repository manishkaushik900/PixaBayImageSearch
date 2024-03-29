package com.pixabay.imagesearch.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pixabay.imagesearch.ui.commons.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
//                val navController = rememberNavController()
                val windowSize = calculateWindowSizeClass(this)
//                NavigationBuilder(navController)
                Navigation(windowSize.widthSizeClass)
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun AppPreview() {
    MaterialTheme {
       /* NavigationBuilder(
            navController = rememberNavController(),
            navController1 = navController
        )*/
    }
}