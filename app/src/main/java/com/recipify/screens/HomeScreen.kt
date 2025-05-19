package com.recipify.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.recipify.ui.theme.Cyan
import com.recipify.ui.theme.sofiaProFamily
import com.recipify.ui.theme.textDarkGreen
import com.recipify.viewmodel.AuthViewModel
import com.recipify.viewmodel.RecipeViewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RecipeViewModel,
    apiKey: String,
    authViewModel: AuthViewModel
) {
    val recipes = viewModel.recipes
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage

    var searchText by remember { mutableStateOf("") }
    var lastQuery by remember { mutableStateOf("") }  // store last query searched

    fun performSearch(query: String) {
        if (query.isNotBlank() && query != lastQuery) {
            lastQuery = query
            viewModel.fetchRecipes(query, apiKey)
        }
    }

    val context = LocalContext.current
    val activity = context as? Activity

    BackHandler {
        activity?.finish()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(top = 30.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                "Search \uD83D\uDD0D",
                color = Color.Black,
                fontFamily = sofiaProFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
            )
            Spacer(Modifier.height(20.dp))

            SearchField(
                searchText = searchText,
                onSearchTextChange = { searchText = it },
                onSearchSubmit = { query ->
                    performSearch(query)
                }
            )

            when {
                isLoading -> {
                    Spacer(Modifier.height(20.dp))
                    Text("Loading...", fontFamily = sofiaProFamily)
                }
                error != null -> {
                    Spacer(Modifier.height(20.dp))
                    Text("Error: $error", color = Color.Red, fontFamily = sofiaProFamily)
                }
                else -> {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        recipes.forEach { recipe ->
                            RecipeCard(
                                recipeName = recipe.title,
                                recipeID = recipe.id,
                                recipeImage = recipe.image,
                                navController = navController
                            )

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchSubmit: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        placeholder = {
            Text(
                text = "Enter ingredients (comma-separated)",
                color = Color.Gray,
                fontFamily = sofiaProFamily,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Clip
            )
        },
        textStyle = TextStyle(
            fontFamily = sofiaProFamily,
            fontWeight = FontWeight.Medium,
            color = textDarkGreen
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search Icon",
                tint = Color.Black
            )
        },
        singleLine = true,
        maxLines = 1,  // Ensures it's strictly one line
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFDDE3EC),
            unfocusedBorderColor = Color(0xFFDDE3EC),
            cursorColor = Cyan,
            selectionColors = TextSelectionColors(handleColor = Cyan, backgroundColor = Cyan)
        ),
        modifier = modifier
            .widthIn(min = 330.dp, max = 330.dp)  // Constrain width even on narrow screens
            .heightIn(min = 56.dp, max = 100.dp)   // Lock height to standard TextField height
            .padding(vertical = 8.dp),

        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),

        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchSubmit(searchText)
                keyboardController?.hide()
            }
        )
    )
}


@Composable
fun RecipeCard(
    modifier: Modifier = Modifier,
    recipeName: String,
    recipeID: Int,
    recipeImage: String? = null,
    navController: NavController
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = textDarkGreen
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
        modifier = modifier
            .padding(start = 25.dp, end = 25.dp, bottom = 15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            AsyncImage(
                model = recipeImage,
                contentDescription = "Food Image",
                contentScale = ContentScale.Crop, // Crop to fill bounds
                modifier = Modifier
                    .fillMaxWidth(0.3f) // Image takes 30% of the row width
                    .aspectRatio(1f) // Maintain 1:1 aspect ratio
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recipeName,
                    fontFamily = sofiaProFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$recipeID",
                    fontFamily = sofiaProFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(textDarkGreen)
                    .size(36.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { navController.navigate("recipeDetail/${recipeID}") },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                        contentDescription = "See Recipe",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
