package com.recipify.data.repository

import com.recipify.data.api.RecipeApi
import javax.inject.Inject
import com.recipify.BuildConfig
import com.recipify.data.model.Recipe
import com.recipify.data.model.RecipeDetail

class RecipeRepository @Inject constructor(
    private val api: RecipeApi
) {
    suspend fun getRecipes(query: String, number: Int, apiKey: String): List<Recipe> {
        return api.getRecipes(query,number = number, apiKey)
    }

    suspend fun getRecipeDetail(recipeId: Int, apiKey: String): RecipeDetail {
        return api.getRecipeDetail(recipeId, apiKey)
    }

}
