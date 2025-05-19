package com.recipify.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.recipify.R
import com.recipify.navigation_service.Screen
import com.recipify.ui.theme.buttonGreen
import com.recipify.ui.theme.sofiaProFamily
import com.recipify.viewmodel.AuthState
import com.recipify.viewmodel.AuthViewModel


@Composable
fun FirstPage(
    modifier: Modifier = Modifier,
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToCreateAccountScreen: () -> Unit,
    authViewModel: AuthViewModel,
    navController: NavController
) {


    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate(Screen.Home.route)
            is AuthState.Error -> {
                Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
                authViewModel.clearAuthState()
            }
            else -> Unit
        }
    }

    val activity = context as? Activity
    BackHandler {
        activity?.finish()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF70b9be))
    ) {
        AsyncImage(
            model = R.drawable.login_pattern,
            contentDescription = "pattern",
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(0.dp, (-50).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = R.drawable.login_food,
                contentDescription = "food images",
                modifier = Modifier.size(350.dp)
            )

        }
        LoginButtons(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            upperBtnColor = buttonGreen,
            lowerBtnColor = Color.Transparent,
            upperBtnText = "Login",
            lowerBtnText = "Create New Account",
            upperBtnTextColor = Color.White,
            lowerBtnTextColor = Color.White,
            upperBtnFunction = onNavigateToLoginScreen,
            lowerBtnFunction = onNavigateToCreateAccountScreen
        )


    }
}

@Composable
fun LoginButtons(
    modifier: Modifier = Modifier,
    upperBtnColor: Color,
    lowerBtnColor: Color,
    upperBtnText: String,
    lowerBtnText: String,
    upperBtnTextColor: Color,
    lowerBtnTextColor: Color,
    upperBtnFunction: () -> Unit,
    lowerBtnFunction: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {


        Button(
            onClick = upperBtnFunction,
            colors = ButtonColors(
                containerColor = upperBtnColor,
                contentColor = Color.White,
                disabledContainerColor = upperBtnColor,
                disabledContentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp)
                .height(60.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                upperBtnText,
                fontFamily = sofiaProFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = upperBtnTextColor,
                textAlign = TextAlign.Center
                )
        }

        Button(
            onClick = lowerBtnFunction,
            colors = ButtonColors(
                containerColor = lowerBtnColor,
                contentColor = Color.White,
                disabledContainerColor = lowerBtnColor,
                disabledContentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp)
                .height(60.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                lowerBtnText,
                fontFamily = sofiaProFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = lowerBtnTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}