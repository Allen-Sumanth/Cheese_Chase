package com.example.cheesechase.navigation

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cheesechase.GameViewModel
import com.example.cheesechase.screens.GamePage
import com.example.cheesechase.screens.HomePage

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: GameViewModel = viewModel<GameViewModel>()

    NavHost(navController = navController, startDestination = Screens.HomePage.route) {

        composable(route = Screens.HomePage.route) {
            HomePage(navController = navController, viewModel = viewModel)
        }

        composable(route = Screens.GamePage.route) {
            GamePage(navController =  navController, viewModel = viewModel)
        }
    }

}