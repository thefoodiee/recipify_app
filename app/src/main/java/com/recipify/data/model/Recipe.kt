package com.recipify.data.model

data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val usedIngredientCount: Int? = null,
    val missedIngredientCount: Int? = null
    // You can also add usedIngredients, missedIngredients if you need them
)
