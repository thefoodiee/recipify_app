package com.recipify

import BottomNavigationBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.recipify.navigation_service.AppNavHost
import com.recipify.navigation_service.Screen
import com.recipify.ui.theme.RecipifyTheme
import com.recipify.viewmodel.AuthViewModel
import com.recipify.viewmodel.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            RecipifyTheme {
                val recipeViewModel: RecipeViewModel = hiltViewModel()
                var homeVisited by rememberSaveable { mutableStateOf(false) }

                WindowCompat.setDecorFitsSystemWindows(window, false)
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
                val navController = rememberNavController()
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

                val showBottomBar = when (currentDestination) {
                    Screen.Home.route,
                    Screen.UserAccount.route -> true
                    else -> false
                }

                val authViewModel : AuthViewModel by viewModels()
                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(navController,modifier = Modifier.navigationBarsPadding())
                        }
                    },
                    containerColor = Color.White
                ) {innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        recipeViewModel = recipeViewModel,
                        onHomeVisited = { homeVisited = true },
                        homeVisited = homeVisited,
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }
}

