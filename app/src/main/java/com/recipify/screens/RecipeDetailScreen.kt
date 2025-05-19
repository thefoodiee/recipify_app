package com.recipify.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.recipify.ui.theme.sofiaProFamily
import com.recipify.ui.theme.textDarkGreen

@Composable
fun RecipeDetailScreen(
    recipeTitle: String,
    recipeImage: String,
    readyInMinutes: Int,
    servings: Int,
    healthScore: Int,
    ingredients: List<String>,
    instructions: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Recipe Image
        AsyncImage(
            model = recipeImage,
            contentDescription = "Recipe Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Title
        Text(
            text = recipeTitle,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = sofiaProFamily,
            color = textDarkGreen
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Info Row
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            InfoItem(label = "Ready In", value = "$readyInMinutes min")
            InfoItem(label = "Servings", value = "$servings")
            InfoItem(label = "Health", value = "$healthScore")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ingredients
        Text(
            text = "Ingredients",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = sofiaProFamily,
            color = textDarkGreen
        )
        Spacer(modifier = Modifier.height(6.dp))
        ingredients.forEach { item ->
            Text("• $item", fontFamily = sofiaProFamily, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Instructions
        Text(
            text = "Instructions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = sofiaProFamily,
            color = textDarkGreen
        )
        Spacer(modifier = Modifier.height(6.dp))
        instructions.forEachIndexed { index, step ->
            Text("${index + 1}. $step", fontFamily = sofiaProFamily, fontSize = 16.sp, modifier = Modifier.padding(bottom = 6.dp))
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = sofiaProFamily)
        Text(label, fontSize = 13.sp, color = Color.Gray, fontFamily = sofiaProFamily)
    }
}
