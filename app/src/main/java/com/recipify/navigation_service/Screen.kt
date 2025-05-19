package com.recipify.navigation_service

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Landing : Screen("landing") // replaces old "home" that used FirstPage
    object Login : Screen("login")
    object CreateAccount : Screen("create_account")
    object Home : Screen("home") // actual HomeScreen
    object UserAccount : Screen("user_account")
}
