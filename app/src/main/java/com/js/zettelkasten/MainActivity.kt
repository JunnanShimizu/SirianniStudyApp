package com.js.zettelkasten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.js.zettelkasten.ui.navigation.MainNavigation
import com.js.zettelkasten.ui.screen.AddScreen
import com.js.zettelkasten.ui.screen.SeeAllScreen
import com.js.zettelkasten.ui.screen.StudyCardsViewModel
import com.js.zettelkasten.ui.screen.StudyScreen
import com.js.zettelkasten.ui.theme.ZettelkastenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZettelkastenTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost()
                }
            }
        }
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainNavigation.StudyScreen.route
){
    val viewModel: StudyCardsViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(MainNavigation.StudyScreen.route) {
            StudyScreen(navController, viewModel)
        }
        composable(MainNavigation.SeeAllScreen.route) {
            SeeAllScreen(navController, viewModel)
        }
        composable(MainNavigation.AddScreen.route) {
            AddScreen(navController, viewModel)
        }
    }
}