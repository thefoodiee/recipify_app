package com.recipify.navigation_service

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.recipify.BuildConfig
import com.recipify.screens.CreateAccountScreen
import com.recipify.screens.FirstPage
import com.recipify.screens.HomeScreen
import com.recipify.screens.LoginScreen
import com.recipify.screens.RecipeDetailScreen
import com.recipify.screens.SplashScreen
import com.recipify.screens.UserAccount
import com.recipify.viewmodel.AuthViewModel
import com.recipify.viewmodel.RecipeViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    recipeViewModel: RecipeViewModel,
    homeVisited: Boolean,
    onHomeVisited: () -> Unit,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Landing.route, enterTransition = { fadeIn(tween(1000)) }) {
            FirstPage(
                onNavigateToLoginScreen = { navController.navigate(Screen.Login.route) },
                onNavigateToCreateAccountScreen = { navController.navigate(Screen.CreateAccount.route) },
                authViewModel = authViewModel,
                navController = navController
            )
        }

        composable(
            Screen.Login.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700)
                )
            }
        ) {
            LoginScreen(
                onNavigateToCreateAccountScreen = { navController.navigate(Screen.CreateAccount.route) },
                onNavigateToHomeScreen = { navController.navigate(Screen.Home.route) },
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(
            Screen.CreateAccount.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }
        ) {
            CreateAccountScreen(
                onNavigateToLoginScreen = { navController.navigate(Screen.Login.route) },
                onNavigateToHomeScreen = { navController.navigate(Screen.Home.route) },
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(
            Screen.Home.route,
            enterTransition = {
                if (!homeVisited) {
                    onHomeVisited()
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        tween(1000)
                    ) + fadeIn(tween(2000))
                } else {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.End,
                        tween(700)
                    )
                }
            }
        ) {
            HomeScreen(
                navController = navController,
                modifier = modifier,
                apiKey = BuildConfig.RECIPE_API_KEY,
                viewModel = recipeViewModel,
                authViewModel = authViewModel
            )
        }

        composable(
            Screen.UserAccount.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }
        ) {
            UserAccount(
                navController = navController,
                modifier = modifier,
                authViewModel = authViewModel
            )
        }

        composable(
            "recipeDetail/{recipeId}",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull() ?: return@composable
            val viewModel: RecipeViewModel = hiltViewModel()
            val recipe = viewModel.selectedRecipe.value

            LaunchedEffect(recipeId) {
                viewModel.fetchRecipeById(recipeId, BuildConfig.RECIPE_API_KEY)
            }

            recipe?.let {
                RecipeDetailScreen(
                    recipeTitle = it.title,
                    recipeImage = it.image,
                    readyInMinutes = it.readyInMinutes,
                    servings = it.servings,
                    healthScore = it.healthScore,
                    ingredients = it.extendedIngredients.map { ing -> ing.original },
                    instructions = it.analyzedInstructions.firstOrNull()?.steps?.map { step -> step.step } ?: emptyList()
                )
            }
        }

    }
}
