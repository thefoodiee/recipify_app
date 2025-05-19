package com.recipify.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.recipify.R
import com.recipify.navigation_service.Screen
import com.recipify.ui.theme.sofiaProFamily
import com.recipify.ui.theme.textDarkGreen
import com.recipify.viewmodel.AuthState
import com.recipify.viewmodel.AuthViewModel


@Composable
fun UserAccount(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val authState = authViewModel.authState.observeAsState()
    val userEmail = authViewModel.currentUserEmail.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.UnAuthenticated -> navController.navigate(Screen.Landing.route)
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header (non-scrollable)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Account",
                color = Color.Black,
                fontFamily = sofiaProFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }

        UserAccountCard(email = userEmail.value ?: "Loading...")

        TextButton(onClick = {
            authViewModel.signout()
        },
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(
                "Sign Out",
                color = Color.Red,
                fontFamily = sofiaProFamily,
                fontSize = 15.sp,
            )
        }

//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 30.dp, vertical = 15.dp)
//        ) {
//            Text(
//                "My Favorites",
//                fontFamily = sofiaProFamily,
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                color = textDarkGreen
//            )
//            TextButton(onClick = {}) {
//                Text(
//                    "See All",
//                    fontFamily = sofiaProFamily,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 13.sp,
//                    color = Cyan
//                )
//            }
//        }

        // Scrollable grid
//        LazyColumn(
//            modifier = Modifier
//                .weight(1f)
//                .padding(bottom = 20.dp)
//        ) {
//            items(favoriteRecipes.chunked(2)) { rowRecipes ->
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(10.dp),
//                    modifier = Modifier
//                        .padding(horizontal = 25.dp, vertical = 5.dp)
//                        .fillMaxWidth()
//                ) {
//                    for (recipe in rowRecipes) {
//                        FavoriteRecipeCard(
//                            title = recipe.title,
//                            id = recipe.id,
//                            imageResId = recipe.imageResId,
//                            modifier = Modifier.weight(1f)
//                        )
//                    }
//
//                    if (rowRecipes.size == 1) {
//                        Spacer(modifier = Modifier.weight(1f))
//                    }
//                }
//            }
//        }
    }
}


//@Composable
//fun FavoriteRecipeCard(
//    modifier: Modifier = Modifier,
//    title: String,
//    id: Int,
//    imageResId: Int
//) {
//    ElevatedCard(
//        shape = RoundedCornerShape(24.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
//        colors = CardDefaults.elevatedCardColors(
//            containerColor = Color.White,
//            contentColor = textDarkGreen
//        ),
//        modifier = modifier
//            .width(180.dp)
//            .height(210.dp)
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Box(
//                modifier = Modifier
//                    .padding(8.dp)
//                    .fillMaxWidth()
//                    .height(120.dp)
//                    .clip(RoundedCornerShape(16.dp))
//            ) {
//                Image(
//                    painter = painterResource(id = imageResId),
//                    contentDescription = "Recipe Image",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
//
//
//                IconButton(
//                    onClick = { },
//                    modifier = Modifier
//                        .align(Alignment.TopEnd)
//                        .padding(top = 6.dp, end = 6.dp)
//                        .background(Color.White, shape = CircleShape)
//                        .size(28.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.Favorite,
//                        contentDescription = "Favorite",
//                        tint = Cyan,
//                        modifier = Modifier.size(16.dp)
//                    )
//                }
//
//            }
//
//            Text(
//                title,
//                fontWeight = FontWeight.Bold,
//                fontFamily = sofiaProFamily,
//                fontSize = 16.sp,
//                color = textDarkGreen,
//                modifier = Modifier
//                    .padding(horizontal = 12.dp),
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis
//            )
//
//            Text(
//                "$id",
//                fontFamily = sofiaProFamily,
//                fontWeight = FontWeight.Normal,
//                fontSize = 14.sp,
//                color = Color.Gray,
//                modifier = Modifier.padding(horizontal = 12.dp)
//            )
//        }
//    }
//}



@Composable
fun UserAccountCard(
    modifier: Modifier = Modifier,
    email: String
) {
    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = textDarkGreen,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 12.dp),

        ) {
            AsyncImage(
                model = R.drawable.default_avatar,
                contentDescription = "Avatar Image",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
//                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(start = 10.dp)

            ) {
                Text(
                    email,
                    fontFamily = sofiaProFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp,
                    color = textDarkGreen,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.99f)
                )

            }


        }
    }
}