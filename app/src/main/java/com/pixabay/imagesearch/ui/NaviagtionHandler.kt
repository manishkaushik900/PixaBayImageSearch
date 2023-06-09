package com.pixabay.imagesearch.ui

import SearchScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pixabay.imagesearch.ui.search.SearchImageDetails


@Composable
fun NavigationBuilder() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.Start.name) {
        composable(AppScreens.Start.name) {
            SearchScreen(onNextButtonClicked = {
                navController.navigate(AppScreens.ImageDetail.name) })
        }

        composable(AppScreens.ImageDetail.name) {
            SearchImageDetails()
        }

    }

}


enum class AppScreens(){
    Start,
    ImageDetail
}