package com.recipify.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipify.data.model.Recipe
import com.recipify.data.model.RecipeDetail
import com.recipify.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repo: RecipeRepository
) : ViewModel() {

    private val recipesPerPage = 20

    var recipes by mutableStateOf<List<Recipe>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun fetchRecipes(query: String, apiKey: String, append: Boolean = false) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val result = repo.getRecipes(query, recipesPerPage, apiKey)
                recipes = if (append) recipes + result else result
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "An error occurred"
            }
            isLoading = false
        }
    }

    private val _selectedRecipe = mutableStateOf<RecipeDetail?>(null)
    val selectedRecipe: State<RecipeDetail?> = _selectedRecipe

    fun fetchRecipeById(recipeId: Int, apiKey: String) {
        viewModelScope.launch {
            try {
                _selectedRecipe.value = repo.getRecipeDetail(recipeId, apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

