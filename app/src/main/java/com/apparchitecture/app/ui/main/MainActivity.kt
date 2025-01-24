package com.apparchitecture.app.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.apparchitecture.app.ui.main.viewmodel.test.MyViewModel
import com.apparchitecture.app.ui.navigation.Routes
import com.apparchitecture.app.ui.screens.detail.DetailScreen
import com.apparchitecture.app.ui.screens.main.MainScreen
import com.indra.wfm.ui.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MyViewModel = hiltViewModel()

                    AppContent(
                        onBackPressed = { onBackPressedDispatcher.onBackPressed() }
                    )
                }
            }
        }
    }

}

@Composable
fun AppContent(onBackPressed: () -> Unit) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Main.route) {
        composable(
            route = Routes.Main.route,
        ) {
            MainScreen{ id ->
                navController.navigate(Routes.Detail.createRoute(id))
            }
        }
        composable(
            route = Routes.Detail.route,
            popEnterTransition = null,
            popExitTransition = exitTransition(),
            arguments = listOf(
                navArgument(Routes.Detail.id) {
                    type = NavType.LongType
                }
            )
        ) { navBackStackEntry ->

            DetailScreen(
                id = navBackStackEntry.arguments?.getLong(Routes.Detail.id)
                    ?: 0,
                onBackPressed = onBackPressed
            )

        }
    }
}

private fun exitTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? =
    {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(800)
        )
    }

private fun enterTransition(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? =
    {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(800)
        )
    }