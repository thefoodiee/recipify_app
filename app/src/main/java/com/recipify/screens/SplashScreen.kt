package com.recipify.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.recipify.R
import com.recipify.ui.theme.Cyan
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    var visible by remember { mutableStateOf(true) }

    // Trigger fade out after delay
    LaunchedEffect(Unit) {
        delay(1500) // Show splash for 1.5 sec
        visible = false
        delay(500)  // Wait for fade-out to complete
        navController.navigate("landing") {
            popUpTo("splash") { inclusive = true }
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cyan)
            .alpha(alpha),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.recipify_logo),
            contentDescription = "Splash Icon",
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Fit
        )
    }
}

