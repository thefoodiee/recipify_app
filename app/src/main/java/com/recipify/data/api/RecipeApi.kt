package com.recipify.data.api

import com.recipify.data.model.Recipe
import com.recipify.data.model.RecipeDetail
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("recipes/findByIngredients")
    suspend fun getRecipes(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int = 20,
        @Query("apiKey") apiKey: String
    ): List<Recipe>

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetail(
        @retrofit2.http.Path("id") recipeId: Int,
        @Query("apiKey") apiKey: String
    ): RecipeDetail
}