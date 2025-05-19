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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.recipify.R
import com.recipify.navigation_service.Screen
import com.recipify.ui.theme.Cyan
import com.recipify.ui.theme.buttonGreen
import com.recipify.ui.theme.textDarkGreen
import com.recipify.ui.theme.sofiaProFamily
import com.recipify.viewmodel.AuthState
import com.recipify.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToCreateAccountScreen: () -> Unit,
    navController: NavController,
    onNavigateToHomeScreen: () -> Unit,
    authViewModel: AuthViewModel
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()

    val email = authViewModel.emailInput
    val password = authViewModel.passwordInput

    BackHandler {
        navController.navigate(Screen.Landing.route) {
            popUpTo(Screen.Landing.route) { inclusive = true }
        }
    }

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
            "Login",
            color = Color.Black,
            fontFamily = sofiaProFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(20.dp)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = R.drawable.login_taco,
                contentDescription = "pattern",
                modifier = Modifier.fillMaxWidth()
            )

            EmailField(emailText = email, onEmailChange = { authViewModel.updateEmailInput(it) })
            PasswordField(passwordText = password, onPasswordChange = { authViewModel.updatePasswordInput(it) })
        }

        LoginButtons(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            upperBtnColor = buttonGreen,
            lowerBtnColor = Color.Transparent,
            upperBtnText = "Login",
            lowerBtnText = "Don't have an account?\nCreate Account",
            upperBtnTextColor = Color.White,
            lowerBtnTextColor = textDarkGreen,
            upperBtnFunction = { authViewModel.login(email, password) },
            lowerBtnFunction = onNavigateToCreateAccountScreen
        )
    }
}



@Composable
fun EmailField(
    emailText: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = emailText,
        onValueChange = onEmailChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
        placeholder = {
            Text(
                text = "Email",
                color = Color.Gray,
                fontFamily = sofiaProFamily,
                fontWeight = FontWeight.Medium
            )
        },
        textStyle = TextStyle(
            fontFamily = sofiaProFamily,
            fontWeight = FontWeight.Medium,
            color = textDarkGreen
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Email Icon",
                tint = Color.Black
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFDDE3EC),
            unfocusedBorderColor = Color(0xFFDDE3EC),
            cursorColor = Cyan,
            selectionColors = TextSelectionColors(handleColor = Cyan, backgroundColor = Cyan)
        ),
        modifier = modifier
            .width(330.dp)
            .padding(vertical = 8.dp)
    )
}


@Composable
fun PasswordField(
    passwordText: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = passwordText,
        onValueChange = onPasswordChange,
        placeholder = {
            Text(
                text = "Password",
                color = Color.Gray,
                fontFamily = sofiaProFamily,
                fontWeight = FontWeight.Medium
            )
        },
        textStyle = TextStyle(
            fontFamily = sofiaProFamily,
            fontWeight = FontWeight.Medium,
            color = textDarkGreen
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Lock Icon",
                tint = Color.Black
            )
        },
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else
                Icons.Filled.VisibilityOff

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = image,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = Color.Gray
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFDDE3EC),
            unfocusedBorderColor = Color(0xFFDDE3EC),
            cursorColor = Cyan,
            selectionColors = TextSelectionColors(handleColor = Cyan, backgroundColor = Cyan)
        ),
        modifier = modifier
            .width(330.dp)
            .padding(vertical = 8.dp)
    )
}
