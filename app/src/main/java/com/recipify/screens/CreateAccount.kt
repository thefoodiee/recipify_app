package com.recipify.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.recipify.R
import com.recipify.navigation_service.Screen
import com.recipify.ui.theme.buttonGreen
import com.recipify.ui.theme.sofiaProFamily
import com.recipify.ui.theme.textDarkGreen
import com.recipify.viewmodel.AuthState
import com.recipify.viewmodel.AuthViewModel
import okhttp3.internal.wait

@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier,
    onNavigateToLoginScreen: () -> Unit,
    navController: NavController,
    onNavigateToHomeScreen: () -> Unit,
    authViewModel: AuthViewModel
) {
    val authState = authViewModel.authState.observeAsState()

    val email = authViewModel.emailInput
    val password = authViewModel.passwordInput

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> onNavigateToHomeScreen()
            is AuthState.Error -> {
                Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
                authViewModel.clearAuthState()
            }
            else -> Unit
        }
    }

    BackHandler {
        navController.navigate(Screen.Landing.route) {
            popUpTo(Screen.Landing.route) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(top = 20.dp)
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }
    ) {

        Text(
            "Create Account",
            color = Color.Black,
            fontFamily = sofiaProFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(20.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = R.drawable.create_account_image,
                contentDescription = "pattern",
                modifier = Modifier
                    .padding(top = 110.dp, bottom = 65.dp)
                    .size(200.dp)
            )

            // email and password fields
            EmailField(emailText = email, onEmailChange = { authViewModel.updateEmailInput(it) })
            PasswordField(passwordText = password, onPasswordChange = { authViewModel.updatePasswordInput(it) })
        }

        LoginButtons(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            upperBtnColor = buttonGreen,
            lowerBtnColor = Color.Transparent,
            upperBtnText = "Create New Account",
            lowerBtnText = "Already have an account?\nLogin",
            upperBtnTextColor = Color.White,
            lowerBtnTextColor = textDarkGreen,
            upperBtnFunction = { authViewModel.signup(email, password) },
            lowerBtnFunction = onNavigateToLoginScreen
        )
    }
}

